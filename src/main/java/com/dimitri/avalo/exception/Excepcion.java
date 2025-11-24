package com.dimitri.avalo.exception;

public class Excepcion extends RuntimeException {

    private String atributo;
    private int statusCode = 400; // por defecto Bad Request

    public Excepcion(String mensaje) {
        super(mensaje);
    }

    public Excepcion(String atributo, String mensaje, int statusCode) {
        super(mensaje);
        this.atributo = atributo;
        this.statusCode = statusCode;
    }

    public String getAtributo() {
        return atributo;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
