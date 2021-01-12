package com.renanalmeida.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.renanalmeida.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar dataBoleto = Calendar.getInstance();
		dataBoleto.setTime(instanteDoPedido);
		dataBoleto.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(dataBoleto.getTime());
	}
}
