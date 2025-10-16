package com.taller7arqui.email.consumer;

import com.taller7arqui.pagos.DTO.CustomerNotification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailConsumerMDB {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailConsumerMDB.class);
    private static final String ASUNTO_CONFIRMACION = "✓ Confirmación de Compra - Taller 7 Arquitectura";
    
    private final JavaMailSender mailSender;
    
    public EmailConsumerMDB(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @KafkaListener(topics = "notificacion-mail", groupId = "email-consumer-group")
    public void onMessage(CustomerNotification notificacion) {
        try {
            logger.info("Procesando notificación de correo para: {}", notificacion.getEmail());
            
            enviarCorreoHTML(notificacion);
            
            logger.info("Correo enviado exitosamente a: {}", notificacion.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando correo a {}: {}", notificacion.getEmail(), e.getMessage(), e);
        }
    }
    
    private void enviarCorreoHTML(CustomerNotification notificacion) throws Exception {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
        
        helper.setTo(notificacion.getEmail());
        helper.setSubject(ASUNTO_CONFIRMACION);
        
        String contenidoHTML = construirContenidoHTML(notificacion);
        helper.setText(contenidoHTML, true);
        
        mailSender.send(mensaje);
    }
    
    private String construirContenidoHTML(CustomerNotification notificacion) {
        String cliente = notificacion.getCliente() != null ? notificacion.getCliente() : "Cliente";
        String listaProductos = construirListaProductos(notificacion);
        String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int cantidadProductos = notificacion.getProductos() != null ? notificacion.getProductos().size() : 0;
        String moneda = notificacion.getMoneda() != null ? notificacion.getMoneda() : "$";
        String totalFormato = String.format("%.2f", notificacion.getTotalPago() != null ? notificacion.getTotalPago() : 0.0);
        
        return getEstiloCSS() + 
            getContenidoHTML(cliente, fechaActual, cantidadProductos, moneda, totalFormato, listaProductos) +
            getCierreHTML();
    }
    
    private String getEstiloCSS() {
        return """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Compra Exitosa</title>
                <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; color: #333; line-height: 1.6; }
                    .container { max-width: 600px; margin: 0 auto; background: white; box-shadow: 0 2px 8px rgba(0,0,0,0.1); border-radius: 8px; overflow: hidden; }
                    .header { background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%); color: white; padding: 40px 20px; text-align: center; }
                    .header h1 { font-size: 28px; margin-bottom: 10px; }
                    .header p { font-size: 14px; opacity: 0.9; }
                    .content { padding: 30px 20px; }
                    .greeting { font-size: 18px; margin-bottom: 20px; }
                    .greeting strong { color: #4CAF50; }
                    .section { margin-bottom: 25px; }
                    .section h3 { font-size: 16px; color: #333; margin-bottom: 15px; border-left: 4px solid #4CAF50; padding-left: 12px; }
                    .resumen-pago { background: #f0f8f0; padding: 15px; border-radius: 6px; border-left: 4px solid #4CAF50; }
                    .resumen-pago p { margin-bottom: 8px; font-size: 15px; }
                    .resumen-pago .total { font-size: 20px; font-weight: bold; color: #4CAF50; margin-top: 10px; padding-top: 10px; border-top: 1px solid #ddd; }
                    .producto { background: #fafafa; padding: 12px; border-radius: 4px; margin-bottom: 10px; border-left: 3px solid #4CAF50; }
                    .producto-nombre { font-weight: bold; color: #333; margin-bottom: 6px; font-size: 15px; }
                    .producto-detalles { font-size: 13px; color: #666; }
                    .info-adicional { background: #e8f5e9; padding: 15px; border-radius: 6px; margin: 20px 0; font-size: 14px; border-left: 4px solid #4CAF50; }
                    .info-adicional strong { display: block; margin-bottom: 8px; color: #333; }
                    .footer { background: #f9f9f9; padding: 20px; text-align: center; border-top: 1px solid #eee; }
                    .footer p { font-size: 14px; color: #666; margin-bottom: 5px; }
                    .footer-logo { font-size: 12px; color: #4CAF50; font-weight: bold; margin-top: 10px; }
                    .footer-disclaimer { margin-top: 15px; font-size: 12px; color: #999; }
                    @media (max-width: 600px) { .container { margin: 10px; } .header { padding: 30px 15px; } .header h1 { font-size: 24px; } .content { padding: 20px 15px; } }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>✓ Compra Exitosa</h1>
                        <p>Tu pedido ha sido procesado correctamente</p>
                    </div>
            """;
    }
    
    private String getContenidoHTML(String cliente, String fechaActual, int cantidadProductos, String moneda, String totalFormato, String listaProductos) {
    return String.format("""
            <div class="content">
                <div class="greeting">
                    Hola <strong>%s</strong>
                </div>
                <p>Nos complace confirmar que tu compra ha sido procesada exitosamente. A continuación encontrarás los detalles de tu pedido.</p>
                <div class="section">
                    <h3>Resumen de tu Pedido</h3>
                    <div class="resumen-pago">
                        <p><strong>Fecha:</strong> %s</p>
                        <p><strong>Cantidad de productos:</strong> %d</p>
                        <div class="total">
                            Total: %s %s
                        </div>
                    </div>
                </div>
                <div class="section">
                    <h3>Productos Comprados</h3>
                    %s
                </div>
                <div class="info-adicional">
                    <strong>Próximos pasos:</strong>
                    Tu pedido será preparado y empacado en las próximas 24 horas. Recibirás un correo de confirmación de envío con el número de seguimiento.
                </div>
                <div class="section">
                    <p style="font-size: 14px; color: #666;">
                        Si tienes alguna pregunta sobre tu pedido, no dudes en contactarnos respondiendo este correo o visitando nuestra página de soporte.
                    </p>
                </div>
            </div>
            """, cliente, fechaActual, cantidadProductos, moneda, totalFormato, listaProductos);
}

    private String getCierreHTML() {
        return """
                    <div class="footer">
                        <p>Gracias por tu compra</p>
                        <div class="footer-logo">Taller 7 Arquitectura</div>
                        <div class="footer-disclaimer">
                            Este es un correo automático, por favor no respondas a esta dirección.
                        </div>
                    </div>
                </div>
            </body>
            </html>
            """;
    }
    
    private String construirListaProductos(CustomerNotification notificacion) {
        if (notificacion.getProductos() == null || notificacion.getProductos().isEmpty()) {
            return "<p style=\"color: #999; font-style: italic;\">No hay productos en este pedido.</p>";
        }
        
        StringBuilder productosHTML = new StringBuilder();
        
        for (Object productoObj : notificacion.getProductos()) {
            if (productoObj instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> producto = (Map<String, Object>) productoObj;
                
                Object nombreProductoObj = producto.get("tituloProductos");//obtenerValor(producto, "nombreProducto", "nombreProducto", "nombre", "Producto");
                Object cantidadObj = producto.get("cantidad");
                Object precioObj = producto.get("precioUnitario");
                Object idObj = producto.get("productoId");
                
                int cantidad = convertirAInt(cantidadObj, 0);
                double precio = convertirADouble(precioObj, 0.0);
                String nombreProducto = convertirAString(nombreProductoObj, "Producto");
                
                productosHTML.append("<div class=\"producto\">")
                           .append("<div class=\"producto-nombre\">").append(nombreProducto).append("</div>")
                           .append("<div class=\"producto-detalles\">")
                           .append("Cantidad: <strong>").append(cantidad).append("</strong> | ")
                           .append("Precio unitario: <strong>$").append(String.format("%.2f", precio)).append("</strong>")
                           .append("</div>")
                           .append("</div>");
            }
        }
        
        return productosHTML.toString();
    }
    
    private String obtenerValor(Map<String, Object> mapa, String... claves) {
        for (String clave : claves) {
            Object valor = mapa.get(clave);
            if (valor != null) {
                return valor.toString();
            }
        }
        return "Producto";
    }
    
    private int convertirAInt(Object valor, int defecto) {
        if (valor instanceof Integer) {
            return (Integer) valor;
        } else if (valor instanceof Number) {
            return ((Number) valor).intValue();
        } else if (valor instanceof String) {
            try {
                return Integer.parseInt((String) valor);
            } catch (NumberFormatException e) {
                logger.warn("No se pudo convertir a int: {}", valor);
            }
        }
        return defecto;
    }
    
    private double convertirADouble(Object valor, double defecto) {
        if (valor instanceof Double) {
            return (Double) valor;
        } else if (valor instanceof Number) {
            return ((Number) valor).doubleValue();
        } else if (valor instanceof String) {
            try {
                return Double.parseDouble((String) valor);
            } catch (NumberFormatException e) {
                logger.warn("No se pudo convertir a double: {}", valor);
            }
        }
        return defecto;
    }
    private String convertirAString(Object valor, String defecto) {
    if (valor == null) {
        return defecto;
    } else if (valor instanceof String) {
        return (String) valor;
    } else if (valor instanceof Number || valor instanceof Boolean || valor instanceof Character) {
        return valor.toString();
    } else {
        try {
            return valor.toString();
        } catch (Exception e) {
            logger.warn("No se pudo convertir a String: {}", valor, e);
            return defecto;
        }
    }
}

}