package org.imooc.errException;

public class ZdException extends Exception{

	public String message;
	
	public ZdException(String message){
        super(message);	//调用父类的构造方法  往上抛异常
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}