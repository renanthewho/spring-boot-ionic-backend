package com.renanalmeida.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.renanalmeida.domain.Cliente;
import com.renanalmeida.domain.enums.TipoCliente;
import com.renanalmeida.dto.ClienteNewDTO;
import com.renanalmeida.repositories.ClienteRepository;
import com.renanalmeida.resources.exceptions.FieldMessage;
import com.renanalmeida.services.validation.utils.BR;

//Criando a validação para a anotação.
public class ClienteValidationValidator implements ConstraintValidator<ClienteValidation, ClienteNewDTO> {

	@Override
	public void initialize(ClienteValidation ann) {
	}
	
	@Autowired
	private ClienteRepository repo;

	@Override     
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) { 
        List<FieldMessage> list = new ArrayList<>();                 
       
        if(objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod())&& 
        		!BR.isValidCPF(objDto.getCpfOuCnpj())) {
        	list.add(new FieldMessage("CpfOuCnpj", "CPF Inválido"));
        }
        
        if(objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod())&& 
        		!BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
        	list.add(new FieldMessage("CpfOuCnpj", "CNPJ Inválido"));
        }
        
        //Validação de e-mail ja existente
        Cliente aux = repo.findByEmail(objDto.getEmail());
        
        if(aux != null) {
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