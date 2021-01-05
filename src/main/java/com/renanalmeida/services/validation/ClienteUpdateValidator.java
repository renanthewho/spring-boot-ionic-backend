package com.renanalmeida.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.renanalmeida.domain.Cliente;
import com.renanalmeida.dto.ClienteDTO;
import com.renanalmeida.repositories.ClienteRepository;
import com.renanalmeida.resources.exceptions.FieldMessage;

//Criando a validação para a anotação.
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Override
	public void initialize(ClienteUpdate ann) {
	}
	
	//Chamada para obter o ID do cliente contido na URI da chamada.
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;

	@Override     
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) { 
        List<FieldMessage> list = new ArrayList<>();
        
       //Map criado para obter o valor do ID na URI. É utilizado um map pois recuperamos chave e valor.
        
        Map<String,String> map = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer UriId = Integer.parseInt(map.get("id"));
        
        //Validação de e-mail ja existente
        Cliente aux = repo.findByEmail(objDto.getEmail());
        
        if(aux != null && !aux.getId().equals(UriId)) {
        	list.add(new FieldMessage("email", "E-mail já existente"));
        }
        
        for (FieldMessage e : list) {         
        	context.disableDefaultConstraintViolation();             
        	context.buildConstraintViolationWithTemplate(e.getMessage()).
        	addPropertyNode(e.getFieldName()).addConstraintViolation();        
        }        
        return list.isEmpty();      
   }
}