package com.renanalmeida.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.renanalmeida.domain.enums.TipoCliente;
import com.renanalmeida.dto.ClienteNewDTO;
import com.renanalmeida.resources.exceptions.FieldMessage;
import com.renanalmeida.services.validation.utils.BR;

//Criando a validação para a anotação.
public class ClienteValidationValidator implements ConstraintValidator<ClienteValidation, ClienteNewDTO> {

	@Override
	public void initialize(ClienteValidation ann) {
	}

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
        
        for (FieldMessage e : list) {         
        	context.disableDefaultConstraintViolation();             
        	context.buildConstraintViolationWithTemplate(e.getMessage()).
        	addPropertyNode(e.getFieldName()).addConstraintViolation();        
        }        
        return list.isEmpty();      
   }
}