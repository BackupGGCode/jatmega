package pl.mazon.jatmega.core.command;

/**
 * Test polega na przeliczeniu wartości
 * a i b. Wynik w odpowiedzi z atmega zawieta c=a+b
 * @author radomir.mazon
 */

public abstract class TestCommand implements ICommand {

	private int a;
	
	private int b;
	
	private int c;
	
	public TestCommand(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	public abstract void onSuccess(int c);
	
	@Override
	public String serialize() {
		return ""+a+","+b;
	}

	@Override
	public void deserialize(String message) {
		c = Integer.parseInt(message);
	}
	
	@Override
	public char getTargetName() {
		return 'X';
	}
}
