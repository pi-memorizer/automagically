import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Main {
	public static void main(String args[]) {
		Game game = new Game();
		Console console = new Console(game);
		console.Draw();
		console.addWindowListener(new WindowListener(){
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {System.exit(0);}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
		});
		while(true) {
			if(game.key['W'])
				game.player.y -= 1.0/12.0;
			if(game.key['A'])
				game.player.x -= 1.0/8.0;
			if(game.key['S'])
				game.player.y += 1.0/12.0;
			if(game.key['D'])
				game.player.x += 1.0/8.0;
			console.Draw();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
	}
}