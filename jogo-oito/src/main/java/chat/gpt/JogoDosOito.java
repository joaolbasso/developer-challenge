package chat.gpt;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class JogoDosOito extends JFrame implements KeyListener, ActionListener {
	private int[][] tabuleiro = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } };
	private JButton[][] botoes = new JButton[3][3];
	private JButton botaoReiniciar;

	public JogoDosOito() {
		super("Jogo dos Oito");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setLayout(new GridLayout(4, 3));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton botao = new JButton();
				botao.setFont(new Font("Arial", Font.BOLD, 36));
				botoes[i][j] = botao;
				//Adicionado listeners a cada botao para identificar click de mouse
				botao.addActionListener(this); 
				add(botao);
			}
		}

		botaoReiniciar = new JButton("Reiniciar");
		botaoReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarJogo();
			}
		});
		add(new JLabel(""));
		add(botaoReiniciar);
		add(new JLabel(""));

		addKeyListener(this);
		setFocusable(true);
		atualizarTabuleiro();
		setVisible(true);
	}

        @Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			mover(1, 0);
			break;
		case KeyEvent.VK_DOWN:
			mover(-1, 0);
			break;
		case KeyEvent.VK_LEFT:
			mover(0, 1);
			break;
		case KeyEvent.VK_RIGHT:
			mover(0, -1);
			break;
		}
	}
        
	private void mover(int linha, int coluna) {
		int linhaVazia = -1;
		int colunaVazia = -1;
		//Identifica o botao vazio
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tabuleiro[i][j] == 0) {
					linhaVazia = i;
					colunaVazia = j;
				}
			}
		}
		int novaLinha = linhaVazia + linha;
		int novaColuna = colunaVazia + coluna;
		if (novaLinha < 0 || novaLinha > 2 || novaColuna < 0 || novaColuna > 2) {
			// movimento inválido
			return;
		}
		tabuleiro[linhaVazia][colunaVazia] = tabuleiro[novaLinha][novaColuna];
		tabuleiro[novaLinha][novaColuna] = 0;
		atualizarTabuleiro();
		
	}

	public static void main(String[] args) {
		new JogoDosOito();
	}

	private boolean jogoConcluido() {
		int count = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tabuleiro[i][j] != count % 9) {
					return false;
				}
				count++;
			}
		}
		return true;
	}

	private boolean movimentarPeca(int linha, int coluna) {
		//Cima
		if (linha > 0 && tabuleiro[linha - 1][coluna] == 0) {
			tabuleiro[linha - 1][coluna] = tabuleiro[linha][coluna];
			tabuleiro[linha][coluna] = 0;
			atualizarTabuleiro();
			return true;
		//Baixo
		} else if (linha < 2 && tabuleiro[linha + 1][coluna] == 0) {
			tabuleiro[linha + 1][coluna] = tabuleiro[linha][coluna];
			tabuleiro[linha][coluna] = 0;
			atualizarTabuleiro();
			return true;
		//Esquerda
		} else if (coluna > 0 && tabuleiro[linha][coluna - 1] == 0) {
			tabuleiro[linha][coluna - 1] = tabuleiro[linha][coluna];
			tabuleiro[linha][coluna] = 0;
			atualizarTabuleiro();
			return true;
		//Direita
		} else if (coluna < 2 && tabuleiro[linha][coluna + 1] == 0) {
			tabuleiro[linha][coluna + 1] = tabuleiro[linha][coluna];
			tabuleiro[linha][coluna] = 0;
			atualizarTabuleiro();
			return true;
		}
		return false;
	}

	private void atualizarTabuleiro() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton botao = botoes[i][j];
				
				int valor = tabuleiro[i][j];
				if (valor == 0) {
					botao.setText("");
				} else {
					botao.setText(String.valueOf(valor));
				}
			}
		}
		if (jogoConcluido()) {
			JOptionPane.showMessageDialog(this, "Parabéns, você venceu!");
			reiniciarJogo();
		}
	}

	private void reiniciarJogo() {
		tabuleiro = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		atualizarTabuleiro();
	}

	@Override
	//Listeners dos botões (peças) // Idenficando qual botao foi clicado
	public void actionPerformed(ActionEvent e) {
		Integer botao = Integer.parseInt(((AbstractButton) e.getSource()).getText());
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tabuleiro[i][j] == botao) {
					//System.out.println("Botão " +  i + " e " + j + " = " + tabuleiro[i][j]);
					movimentarPeca(i, j);
					return;
				}
				}
			}
			
	}

	@Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

	}
}

