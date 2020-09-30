package com.lambdasys.food.api.exceptionhandler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.lambdasys.food.domain.exceptions.EntidadeEmUsoException;
import com.lambdasys.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.lambdasys.food.domain.exceptions.NegocioException;

@ControllerAdvice
@SuppressWarnings("serial")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL
	= "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
			+ "o problema persistir, entre em contato com o administrador do sistema.";	
	
	
	@Autowired
	protected MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request) {

	    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    BindingResult br = ex.getBindingResult();
	    List<Field> fields = br.getFieldErrors().stream().map( fe ->  { 
	    	String message = messageSource.getMessage( fe , LocaleContextHolder.getLocale() );
	    	return Field.builder()
	    			.name( fe.getField() )
	    			.userMessage( message ).build(); 
	    	}).collect(Collectors.toList());
	    Problem problem = createProblemBuilder(status, problemType, detail,detail,fields);
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail,detail);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}	

	protected ResponseEntity<Object> handleNoHandlerException( 
			Exception ex , 
			WebRequest request ){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = String.format("Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contacto com o administrador do sistema.");
		Problem problem = createProblemBuilder(status, problemType, detail,MSG_ERRO_GENERICA_USUARIO_FINAL);
		ex.printStackTrace();
		return handleExceptionInternal(ex, problem, new HttpHeaders() , status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException( 
			NoHandlerFoundException ex , 
			HttpHeaders headers , 
			HttpStatus status , 
			WebRequest request ){
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());
		Problem problem = createProblemBuilder(status, problemType, detail,MSG_ERRO_GENERICA_USUARIO_FINAL);
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException ex, 
			HttpHeaders headers,
			HttpStatus status, 
			WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}	
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, 
			HttpHeaders headers,
			HttpStatus status, 
			WebRequest request) {

		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}	
	
	protected ResponseEntity<Object> handleHttpMessageNotReadable( 
			HttpMessageNotReadableException ex ,
			HttpHeaders headers , 
			HttpStatus status , 
			WebRequest request ){
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if( rootCause instanceof InvalidFormatException ) {
			return handleInvalidFormatException((InvalidFormatException)rootCause,headers,status,request);
		}
		if( rootCause instanceof PropertyBindingException ) {
			return handlePropertyBindingException((PropertyBindingException)rootCause,headers,status,request);
		}
		
		String detail = "O corpo da requisição está inválido. Verifique o erro de sintaxe.";
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL , detail,MSG_ERRO_GENERICA_USUARIO_FINAL);
		return handleExceptionInternal(ex, problem , new HttpHeaders() , status , request );
	}
	

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		// Criei o método joinPath para reaproveitar em todos os métodos que precisam
		// concatenar os nomes das propriedades (separando por ".")
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' não existe. "
				+ "Corrija ou remova essa propriedade e tente novamente.", path);

		Problem problem = createProblemBuilder(status, problemType, detail,MSG_ERRO_GENERICA_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(
			InvalidFormatException ex ,
			HttpHeaders headers , 
			HttpStatus status , 
			WebRequest request ){
		String detail = String.format(
				"A propriedade %s recebeu o valor '%s' que é de um tipo invalido. Corrija e informe  um valor compatível com o tipo %s.", 
				ex.getPath().stream().map( ref -> ref.getFieldName() ).collect(Collectors.joining(".")) ,
				ex.getValue() , 
				ex.getTargetType().getSimpleName() );
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL , detail,MSG_ERRO_GENERICA_USUARIO_FINAL);
		return handleExceptionInternal(ex, problem , new HttpHeaders() , status , request );
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException ex ,  WebRequest request ) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO , detail,MSG_ERRO_GENERICA_USUARIO_FINAL);
		return handleExceptionInternal(ex, problem , new HttpHeaders() , status , request );
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(
			EntidadeNaoEncontradaException ex , WebRequest request ) {
		HttpStatus status = HttpStatus.CONFLICT;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO , detail,MSG_ERRO_GENERICA_USUARIO_FINAL);
		return handleExceptionInternal(ex, problem , new HttpHeaders() , status , request );
	}
	
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex , WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO , detail,MSG_ERRO_GENERICA_USUARIO_FINAL);
		return handleExceptionInternal(ex, problem , new HttpHeaders() , status , request );
	}
		
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex , Object body  , HttpHeaders headers , HttpStatus status , WebRequest request ){
		if( Objects.isNull( body ) ) {
			body = Problem.builder()
					.status( status.value() )
					.title(status.getReasonPhrase())
					.build();
		}else if( body instanceof String ) {
			body = Problem.builder()
					.status( status.value() )
					.title((String)body)
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem createProblemBuilder( HttpStatus status , ProblemType problemType , String detail ){
		return Problem.builder()
					.status(status.value())
					.type(problemType.getUri())
					.title(problemType.getTitle())
					.detail(detail)
				.build();
	}

	private Problem createProblemBuilder( HttpStatus status , ProblemType problemType , String detail , String userMessage ){
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail)
				.userMessage(userMessage)
				.build();
	}

	private Problem createProblemBuilder( HttpStatus status , ProblemType problemType , String detail , String userMessage , List<Field> fields ){
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail)
				.userMessage(userMessage)
				.fields(fields)
				.build();
	}
	
	
	
	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}	
	
	
}
