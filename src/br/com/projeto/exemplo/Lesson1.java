package br.com.projeto.exemplo;

/********************* BIBLIOTECAS UTILIZADAS **********************/
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.vecmath.*;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

public class Lesson1 extends JFrame implements KeyListener
{
	/*---------------------------- ATRIBUTOS -------------------------*/
	/*Universo Virtual*/
	private VirtualUniverse	universe;
	
	/*Ponto de referência no mundo virtual - localização dos objetos)*/
	private Locale locale;
	
	/*Objeto que faz a ligação entre o universo virtual e a janela*/
	private Canvas3D canvas3D;
	
	/*Objetos que fazem parte do subgrafo de contexto*/
	private BranchGroup objRoot;
	private ColorCube colorCube;

	/*Variável que controla o estilo da tela: true (modo FullScreen), false (modo janela)*/
	boolean screenMode=false;

	/*-----------------------------------------------------------------*/
 
	/*--------------------------- CONSTRUTOR --------------------------*/
	public Lesson1()
	{
		/************************ PROGRAMAÇÃO JAVA **********************/

		/*Utilização de um objeto Dimension para obter as configurações da tela*/
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		/*Ajusta o tamanho da janela*/
		this.setSize(dimension.width,dimension.height);

		/*Retira as bordas da janela, criando o modo FullScreen*/
		if (screenMode)
		{
			this.setUndecorated(true);
		}
		else
		{
			this.setTitle("API Java3D – Primeiro Programa");
		}

		/*Trata os eventos do teclado*/
		addKeyListener(this);
	
		/************************* PROGRAMAÇÃO EM JAVA3D ****************/
		
		/*Cria o universo virtual*/
		universe = new VirtualUniverse();

		/*Cria o Locale e anexa-o ao mundo virtual*/
		locale = new Locale(universe); 

		/*Cria o objeto canvas3D*/
		GraphicsConfigTemplate3D g3d = new GraphicsConfigTemplate3D();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		GraphicsConfiguration gcn = defaultScreen.getBestConfiguration(g3d);

		canvas3D = new Canvas3D(gcn);
		/*Ajusta a cor do canvas3D*/
		canvas3D.setBackground(Color.black);
		/*Ajusta a dimensao do canvas 3D (mesmo tamanho da janela)*/
		canvas3D.setSize(dimension.width, dimension.height);

		/*Adicionando o subgrafo de visualização*/
		locale.addBranchGraph(createViewGraph());

		/*adicionando o subrafo de contexto*/
		locale.addBranchGraph(createContextGraph());

		/************************** PROGRAMAÇÃO JAVA ********************/
	
		/*Adiciona o canvas3D ao tratador de eventos do teclado*/
		canvas3D.addKeyListener(this);
		
		/*Adiciona o canvas3D a janela e o exibe*/
		this.getContentPane().add(canvas3D);
		this.show();
	}

	/*-----------------------------------------------------------------*/
	
	/*--------------------------- METODOS -----------------------------*/

	/*Método que cria o subgrafo de visualização*/
	BranchGroup createViewGraph()
	{
		/*Cria um objeto BranchGroup*/
		BranchGroup viewPlatformBG = new BranchGroup();
  
		/*Cria um objeto View*/
		View view = new View();	 
 		 
		/*Cria um objeto ViewPlataform*/
		ViewPlatform viewPlatform = new ViewPlatform();	 
 		 
		/*Cria um objeto PhysicalBody*/
		PhysicalBody physicalBody = new PhysicalBody(new Point3d(0, 0, 0),new Point3d(0, 0, 0)); 
  
		/*Cria um objeto PhysicalEnvironment*/
		PhysicalEnvironment physicalEnvironment = new PhysicalEnvironment();
 		
		/*Adiciona vewPlatform ao view*/
		view.attachViewPlatform(viewPlatform);
		/*Adiciona physicalBody ao view*/
		view.setPhysicalBody(physicalBody);
		/*Adiciona physicalEnvironment ao view*/
		view.setPhysicalEnvironment(physicalEnvironment);
		/*Adiciona canvas ao view*/
		view.addCanvas3D(canvas3D);
 
		/*Cria um objeto Transform3D */
		Transform3D transform3D = new Transform3D();
		/*Configura o Transform3D com o Vector3f*/
		transform3D.set(new Vector3f(0.0f, 0.0f, 10.0f));
		/*Cria um objeto TransformGroup*/
		TransformGroup viewPlatformTG = new TransformGroup(transform3D);
		/*Adiciona o ViewPlatform ao TransformGroup*/
		viewPlatformTG.addChild(viewPlatform);
			
		/*Adiciona o TransformGroup ao BranchGroup*/
		viewPlatformBG.addChild(viewPlatformTG);

		/*Retorna o BranchGroup contendo o subgrafo de visualização*/
		return viewPlatformBG;
	}

	/*Metodo que cria o subgrafo de contexto*/
	BranchGroup createContextGraph()
	{
		/*Cria o branchgroup do subgrafo de contexto*/
		BranchGroup contextGraph = new BranchGroup();

		/*Cria um cubo colorido e o adiciona ao branchgroup do subgrafo de contexto*/
		colorCube = new ColorCube();
		contextGraph.addChild(colorCube);

		/*Retorna o BranchGroup contendo o subgrafo de contexto*/
		return contextGraph;
	}

	/*Metodo que trata os eventos de tecla pressionada*/
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			System.exit(1);
		}
	}

	/*Metodo que trata os eventos de tecla pressionada e liberada*/
	public void keyReleased(KeyEvent e)
	{

	}

	/*Metodo que trata dos eventos de teclas que não são de ação (CAPS, HOME, END)*/
	public void keyTyped(KeyEvent e)
	{
	
	}

	/*Método principal*/
	public static void main(String[] args) 
	{
		new Lesson1();
	}
	/*-----------------------------------------------------------------*/
}
