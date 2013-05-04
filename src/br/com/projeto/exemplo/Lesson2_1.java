package br.com.projeto.exemplo;
/*  *  *
 * Curso de desenvolvimento de jogos em Java3D - Exemplo 2
 * @Autor: Silvano Malfatti
 * @Descri��o: este programa define v�rias vis�es de camera para uma mesma cena gr�fica
 * @Data: 29/11/2005
 *  *  */

/********************* BIBLIOTECAS UTILIZADAS **********************/
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.vecmath.*;

public class Lesson2_1 extends JFrame implements KeyListener
{
	/*---------------------------- ATRIBUTOS -------------------------*/
	/* Universo Virtual */
	private VirtualUniverse	universe;
	
	/* Ponto de refer�ncia no mundo virtual - localiza��o dos objetos) */
	private Locale locale;
	
	/* Objeto que faz a liga��o entre o universo virtual e a janela */
	private Canvas3D canvasLeft, canvasRight, canvasFront, canvasBack;
	
	/* objetos utilizados no gerenciamento de interface */
	private GridBagLayout gridLayout;
	private Container container;
	private GridBagConstraints position;
	
	/* Objeto que fazem parte do subgrafo de contexto */
	private ColorCube colorCube;

	/* Vari�vel que controla o estilo da tela: true (FullScreen), false (janela) */
	boolean screenMode=false;
 
	/*--------------------------- CONSTRUTOR --------------------------*/
	public Lesson2_1()
	{
		/************************ PROGRAMA��O JAVA **********************/

		/*Utiliza��o de um objeto Dimension para obter as configura��es da tela*/
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
			this.setTitle("Curso de Desenvolvimento de Jogos com Java + Java3D - Manipulando m�ltiplas vis�es de c�mera");
		}

		/*Trata os eventos do teclado*/
		addKeyListener(this);
	
		/************************* PROGRAMA��O EM JAVA3D ****************/
		
		/*Cria o universo virtual*/
		universe = new VirtualUniverse();

		/*Cria o Locale e anexa-o ao mundo virtual*/
		locale = new Locale(universe); 

		/*Obt�m caracteristicas do dispositivo gr�fico utilizado: monitor*/
		GraphicsConfigTemplate3D g3d = new GraphicsConfigTemplate3D();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getBestConfiguration(g3d);
		
		/*Cria o objeto canvas3D da camera direita e configura suas propriedades*/
		canvasRight = new Canvas3D(gc);
		canvasRight.setBackground(Color.yellow);
		canvasRight.setSize(dimension.width/2, dimension.height/2);
		canvasRight.addKeyListener(this);

		/*Cria o objeto canvas3D da camera esquerda e configura suas propriedades*/
		canvasLeft = new Canvas3D(gc);
		canvasLeft.setBackground(Color.blue);
		canvasLeft.setSize(dimension.width/2, dimension.height/2);
		canvasLeft.addKeyListener(this);

		/*Cria o objeto canvas3D da camera frontal e configura suas propriedades*/
		canvasFront = new Canvas3D(gc);
		canvasFront.setBackground(Color.red);
		canvasFront.setSize(dimension.width/2, dimension.height/2);
		canvasFront.addKeyListener(this);

		/*Cria o objeto canvas3D da camera traseira e configura suas propriedades*/
		canvasBack = new Canvas3D(gc);
		canvasBack.setBackground(Color.green);
		canvasBack.setSize(dimension.width/2, dimension.height/2);
		canvasBack.addKeyListener(this);

		/*Adiciona os subgrafos de visualiza��o ao Locale*/
		locale.addBranchGraph(createBackViewGraph());
		locale.addBranchGraph(createFrontViewGraph());
		locale.addBranchGraph(createLeftViewGraph());
		locale.addBranchGraph(createRightViewGraph());

		/*Adicionando o subrafo de contexto ao Locale*/
		locale.addBranchGraph(createContextGraph());

		/************************** PROGRAMA��O JAVA ********************/
	
		/*Cria os objetos responsaveis pelo gerenciamento da interface*/
		container= this.getContentPane();
		gridLayout = new GridBagLayout();
		container.setLayout(gridLayout);
		position = new GridBagConstraints();

		/*Ajusta a posicao dos objetos canvas3D na tela*/
		
		/*Canvas3D esquerdo*/
		position.gridy=0; 
		position.gridx=0;
		position.gridwidth=1;
		position.gridheight=1;
		position.fill=GridBagConstraints.BOTH;
		gridLayout.setConstraints(canvasLeft,position);
		container.add(canvasLeft);

		/*Canvas3D direito*/
		position.gridy=0; 
		position.gridx=1;
		position.gridwidth=1;
		position.gridheight=1;
		position.fill=GridBagConstraints.BOTH;
		gridLayout.setConstraints(canvasRight,position);
		container.add(canvasRight);

		/*Canvas3D frontal*/
		position.gridy=1;
		position.gridx=0;
		position.gridwidth=1;
		position.gridheight=1;
		position.fill=GridBagConstraints.BOTH;
		gridLayout.setConstraints(canvasFront,position);
		container.add(canvasFront);

		/*Canvas3D traseiro*/
		position.gridy=1;
		position.gridx=1;
		position.gridwidth=1;
		position.gridheight=1;
		position.fill=GridBagConstraints.BOTH;
		gridLayout.setConstraints(canvasBack,position);
		container.add(canvasBack);

		/*Trata o botao fechar no caso de modo janela*/
		addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e)
			{		
				System.exit(0); 
			} 						
		}
		);

		/*Exibe a janela*/
		this.show();
	}

	/*-----------------------------------------------------------------*/
	
	/*--------------------------- METODOS -----------------------------*/

	/*M�todo que cria o subgrafo de visualiza��o da camera esquerda*/
	BranchGroup createLeftViewGraph()
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
 		
		/*Adiciona viewPlatform ao view*/
		view.attachViewPlatform(viewPlatform);
		/*Adiciona physicalBody ao view*/
		view.setPhysicalBody(physicalBody);
		/*Adiciona physicalEnvironment ao view*/
		view.setPhysicalEnvironment(physicalEnvironment);
		/*Adiciona canvas ao view*/
		view.addCanvas3D(canvasLeft);
 
		/*Cria um objeto Transform3D */
		Transform3D transform3D = new Transform3D();
		/*Configura o Transform3D com o Vector3f*/
		transform3D.set(new Vector3f(0.0f, 0.0f, 15.0f));
		/*Cria um objeto TransformGroup*/
		TransformGroup viewPlatformTG = new TransformGroup(transform3D);
		/*Adiciona o ViewPlatform ao TransformGroup*/
		viewPlatformTG.addChild(viewPlatform);
		 
		/*Adiciona o TransformGroup ao BranchGroup*/
		viewPlatformBG.addChild(viewPlatformTG);

		/*Retorna o BranchGroup contendo o subgrafo de visualiza��o*/
		return viewPlatformBG;
	}

	/*M�todo que cria o subgrafo de visualiza��o da camera direita*/
	BranchGroup createRightViewGraph()
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
 		
		/*Adiciona viewPlatform ao view*/
		view.attachViewPlatform(viewPlatform);
		/*Adiciona physicalBody ao view*/
		view.setPhysicalBody(physicalBody);
		/*Adiciona physicalEnvironment ao view*/
		view.setPhysicalEnvironment(physicalEnvironment);
		/*Adiciona canvas ao view*/
		view.addCanvas3D(canvasRight);
 
		/*Cria um objeto Transform3D */
		Transform3D transform3D = new Transform3D();
		/*Configura o Transform3D com o Vector3f*/
		transform3D.set(new Vector3f(0.0f, 0.0f, 15.0f));
		/*Cria um objeto TransformGroup*/
		TransformGroup viewPlatformTG = new TransformGroup(transform3D);
		/*Adiciona o ViewPlatform ao TransformGroup*/
		viewPlatformTG.addChild(viewPlatform);
		 
		/*Adiciona o TransformGroup ao BranchGroup*/
		viewPlatformBG.addChild(viewPlatformTG);

		/*Retorna o BranchGroup contendo o subgrafo de visualiza��o*/
		return viewPlatformBG;
 	}

	/*M�todo que cria o subgrafo de visualiza��o da camera frontal*/
	BranchGroup createFrontViewGraph()
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
 		
		/*Adiciona viewPlatform ao view*/
		view.attachViewPlatform(viewPlatform);
		/*Adiciona physicalBody ao view*/
		view.setPhysicalBody(physicalBody);
		/*Adiciona physicalEnvironment ao view*/
		view.setPhysicalEnvironment(physicalEnvironment);
		/*Adiciona canvas ao view*/
		view.addCanvas3D(canvasFront);
 
		/*Cria um objeto Transform3D */
		Transform3D transform3D = new Transform3D();
		/*Configura o Transform3D com o Vector3f*/
		transform3D.set(new Vector3f(0.0f, 0.0f, 15.0f));
		/*Cria um objeto TransformGroup*/
		TransformGroup viewPlatformTG = new TransformGroup(transform3D);
		/*Adiciona o ViewPlatform ao TransformGroup*/
		viewPlatformTG.addChild(viewPlatform);
		 
		/*Adiciona o TransformGroup ao BranchGroup*/
		viewPlatformBG.addChild(viewPlatformTG);

		/*Retorna o BranchGroup contendo o subgrafo de visualiza��o*/
		return viewPlatformBG;
 	}

	/*M�todo que cria o subgrafo de visualiza��o da camera traseira*/
	BranchGroup createBackViewGraph()
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
 		
		/*Adiciona viewPlatform ao view*/
		view.attachViewPlatform(viewPlatform);
		/*Adiciona physicalBody ao view*/
		view.setPhysicalBody(physicalBody);
		/*Adiciona physicalEnvironment ao view*/
		view.setPhysicalEnvironment(physicalEnvironment);
		/*Adiciona canvas ao view*/
		view.addCanvas3D(canvasBack);
 
		/*Cria um objeto Transform3D */
		Transform3D transform3D = new Transform3D();
		/*Configura o Transform3D com o Vector3f*/
		transform3D.set(new Vector3f(0.0f, 0.0f, 15.0f));
		/*Cria um objeto TransformGroup*/
		TransformGroup viewPlatformTG = new TransformGroup(transform3D);
		/*Adiciona o ViewPlatform ao TransformGroup*/
		viewPlatformTG.addChild(viewPlatform);
		 
		/*Adiciona o TransformGroup ao BranchGroup*/
		viewPlatformBG.addChild(viewPlatformTG);

		/*Retorna o BranchGroup contendo o subgrafo de visualiza��o*/
		return viewPlatformBG;
	}

	/*M�todo que cria o subgrafo de contexto*/
	BranchGroup createContextGraph()
	{
		/*Cria o branchGroup do subgrafo de contexto*/
		BranchGroup contextGraph = new BranchGroup();
		
		/*Cria um cubo colorido e o adiciona ao branchGroup do subgrafo de contexto*/
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

	/*Metodo que trata dos eventos de teclas que n�o s�o de a��o (CAPS, HOME, END)*/
	public void keyTyped(KeyEvent e)
	{
	
	}

	/*M�todo principal*/
	public static void main(String[] args) 
	{
		new Lesson2_1();
	}
}