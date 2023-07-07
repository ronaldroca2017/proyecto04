package com.nttdata.accountmicroservice.util;

public class Constantes {
    private Constantes(){}

    public static final String PERSON_TYPE_PERSONAL = "01";
    public static final String PERSON_TYPE_BUSINESS = "02";
    public static final String PERSON_VIP_TYPE_PERSONAL = "03";
    public static final String PERSON_MYPE_TYPE_BUSINESS = "04";


    public static final String CODE_SUCCESS = "1";
    public static final String CODE_ERROR = "0";

    public static final String SAVE_SUCCESS = "Registro exitoso";
    public static final String SAVE_VALIDATION_COUNT_ACCOUNT = "la cuenta del cliente no puede tener más de una cuenta bancaria\"";
    public static final String SAVE_VALIDATION_CURRENT_ACCOUNT = "sólo puede tener cuentas corrientes";

    public static final String DEPOSITO = "D";
    public static final String RETIRO = "R";
    public static final String PAGO = "P";
    public static final String CONSUMO = "C";

    public static final String CUENTAS_BANCARIAS = "CUENTAS BANCARIAS";
    public static final String CREDITOS = "CREDITOS";
    public static final String CURRENT_ACCOUNT = "CUENTA CORRIENTE";

    public static final String TARJETA_CREDITO_PERSONAL = "tarjeta credito personal";
    public static final String TARJETA_CREDITO_EMPRESARIAL = "tarjeta credito empresarial";

    public static final Integer MAXIMO_TRANSACCIONES = 20;
    public static final Double COMISION = 10.0;

    public static final String MESSAGE_CLIENT_NOT_REGISTER = "The client is not registered";
    public static final String MESSAGE_PRODUCT_NOT_REGISTER = "The product is not registered";

    public static final String ACCOUNT_PRODUCTYPE_EXISTS = "YA TIENE UNA CUENTA REGISTRADA DEL MISMO TIPO";
    public static final String MESSAGE_ACCOUNT_CLIENTE_EMPRESARIAL = "cliente businness can only have current account";
    public static final String MESSAGE_CLIENT_NOT_ACCOUNT = "the client entered does not have any account in the bank";


    public static final String CREDITO_PERSONAL = "PERSONAL";
    public static final String CREDITO_STATUS_PENDIENTE = "PENDIENTE";
    public static final String CREDITO_STATUS_PAGADO = "PAGADO";
    public static final String CREDITO_STATUS_VENCIDO = "VENCIDO";

    public static final String CHECK_STATUS_INVALID = "the client cannot obtain another product because he has a past due debt";

}
