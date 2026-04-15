package condominiosDemo.support;

public class ErrorStepException extends RuntimeException{

    private String messageOriginal;


    //Constructor con mensaje personalizado
    public ErrorStepException(String message){
        super(message);
    }

    //Constructor con mensaje personalizado y mensaje original
    public ErrorStepException(String message, String messageOriginal){
        super(message);
        this.messageOriginal = messageOriginal;
    }

    // Getter para el mensaje original
    public String getMessageOriginal() {
        return messageOriginal;
    }
}
