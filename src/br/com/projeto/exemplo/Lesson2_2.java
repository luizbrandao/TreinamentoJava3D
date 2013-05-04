package br.com.projeto.exemplo;
/*  *  *
 * Curso de desenvolvimento de jogos em Java3D - Exemplo 3
 * @Autor: Silvano Malfatti
 * @Descrição: este programa define um grafo de cena dinâmico no qual cenas e objetos podem ser adicionados dinamicamente
 * @Data: 07/12/2005
 *  *  */

/********************* BIBLIOTECAS UTILIZADAS **********************/
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.vecmath.*;

public class Lesson2_2 extends JFrame implements ActionListener
{
	/*---------------------------- ATRIBUTOS -------------------------*/
	/*Universo Virtual*/
	private VirtualUniverse	universe;
	
	/*Ponto de referência no mundo virtual - localização dos objetos)*/
	private Locale locale;
	
	/*Objeto que faz a ligação entre o universo virtual e a janela*/
	private Canvas3D canvas3D;

	/*objetos utilizados no gerenciamento de interface*/
	private GridBagLayout gridLayout;
	private Container container;
	private GridBagConstraints position;

	/* Botões na janela */
	private JButton button1, button2, button3, button4;
	
	/* Cria os branchgroups do subgrafo de contexto */
	BranchGroup scene1, scene2;
	
	/* Objetos que fazem parte dos subgrafos de contexto */
	BranchGroup blueCone, yellowCone, redSphere, greenSphere;

	/* Variável que controla o estilo da tela: true (FullScreen), false (janela) */
	boolean screenMode=false;

	/*-----------------------------------------------------------------*/
 
	/*--------------------------- CONSTRUTOR --------------------------*/
	public Lesson2_2(){
		/************************ PROGRAMAÇÃO JAVA **********************/
		/* Utilização de um objeto Dimension para obter as configurações da tela */
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		/* Ajusta o tamanho da janela */
		this.setSize(dimension.width,dimension.height);

		/* Retira as bordas da janela, criando o modo FullScreen */
		if (screenMode){
			this.setUndecorated(true);
		} else {
			this.setTitle("Curso de Desenvolvimento de Jogos com Java + Java3D - Manipulando o grafo de cena em tempo de execução");
		}
		
		/************************* PROGRAMAÇÃO EM JAVA3D ****************/
		/* Cria o universo virtual */
		universe = new VirtualUniverse();

		/* Cria o Locale e anexa-o ao mundo virtual */
		locale = new Locale(universe); 

		/*Obtem caracteristicas do dispositivo gráfico utilizado: monitor*/
		GraphicsConfigTemplate3D g3d = new GraphicsConfigTemplate3D();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getBestConfiguration(g3d);

		/*Cria o objeto canvas3D e configura suas propriedades*/
		canvas3D = new Canvas3D(gc);
		canvas3D.setBackground(Color.green);
		canvas3D.setSize(dimension.width, dimension.height/2);

		/*Cria o branchGroup para a esfera verde*/
		greenSphere = new BranchGroup();
		
		/*Cria o branchGroup para a esfera vermelha*/
		redSphere = new BranchGroup();

		/*Cria o branchGroup para a esfera azul*/
		blueCone = new BranchGroup();
		/*Ajusta as permissoes, diz que o branchgroup podera ser removido*/
		blueCone.setCapability(BranchGroup.ALLOW_DETACH);
		
		/*Cria o branchGroup para a esfera amarela*/
		yellowCone = new BranchGroup();
		/*Ajusta as permissoes, diz que o branchgroup podera ser removido*/
		yellowCone.setCapability(BranchGroup.ALLOW_DETACH);

		/*Cria os dois branchGroups das cenas*/
		scene1 = new BranchGroup();
		scene2 = new BranchGroup();

		/*Ajusta as permissões: branchGroup da cena1 podera ser 
		 removido, alterar informacoes a respeito dos seus filhos e receber filhos*/
		scene1.setCapability(BranchGroup.ALLOW_DETACH);
		scene1.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		scene1.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		
		/*Ajusta as permissões: branchGroup da cena2 podera ser 
		 removido, alterar informacoes a respeito dos seus filhos e receber filhos*/
		scene2.setCapability(BranchGroup.ALLOW_DETACH);
		scene2.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		scene2.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);

		/*Cria os subgrafos de contexto para a cena1 e cena2*/
		createContextGraph1();
		createContextGraph2();

		/*Adiciona o subgrafo de visualização ao Locale*/
		locale.addBranchGraph(createViewGraph());

		/*Adiciona o subgrafo de contexto ao Locale (o programa inicia com a cena1 ativa)*/
		locale.addBranchGraph(scene1);

		/************************** PROGRAMAÇÃO JAVA ********************/

		//Cria os objetos responsaveis pelo gerenciamento da interface
		container= this.getContentPane();
		gridLayout = new GridBagLayout();
		container.setLayout(gridLayout);
		position = new GridBagConstraints();

		/*Cria os botoes que alteram a cena 1*/
		button1 = new JButton(" Cena 1 ");
		button2 = new JButton(" Cone 1 ");
		
		/*Cria os botoes que alteram a cena 2*/
		button3 = new JButton(" Cena 2 ");
		button4 = new JButton(" Cone 2 ");

		/*Esta classe mesmo tratará os eventos do mouse, adiciona os componentes a ela*/
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);

		/*Ajusta a posicao dos botoes e do canvas3D na tela*/
		
		/*Botao da cena1*/
		position.gridy=0;
		position.gridx=0;
		position.gridwidth=1;
		position.gridheight=1;
		position.fill=GridBagConstraints.NONE;
		gridLayout.setConstraints(button1,position);
		container.add(button1);

		/*Botao do cone da cena1*/
		position.gridy=0;
		position.gridx=2;
		position.gridwidth=2;
		position.gridheight=1;
		position.fill=GridBagConstraints.NONE;
		gridLayout.setConstraints(button2,position);
		container.add(button2);

		/* Botao da cena2 */
		position.gridy=0;
		position.gridx=4;
		position.gridwidth=1;
		position.gridheight=1;
		position.fill=GridBagConstraints.NONE;
		gridLayout.setConstraints(button3,position);
		container.add(button3);

		/* Botao do cone da cena2 */
		position.gridy=0;
		position.gridx=6;
		position.gridwidth=1;
		position.gridheight=1;
		position.fill=GridBagConstraints.NONE;
		gridLayout.setConstraints(button4,position);
		container.add(button4);

		/* Canvas3D */
		position.gridy=3;
		position.gridx=0;
		position.gridwidth=8;
		position.gridheight=1;
		position.fill=GridBagConstraints.BOTH;
		gridLayout.setConstraints(canvas3D,position);
		container.add(canvas3D);

		/* Trata o botao fechar no caso de modo janela */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0); 
			} 						
		});

		/* Exibe a janela */
		this.show();
	}

	/*Método que cria o subgrafo de visualização*/
	BranchGroup createViewGraph(){
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
		view.addCanvas3D(canvas3D);
 
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

		/*Retorna o BranchGroup contendo o subgrafo de visualização*/
		return viewPlatformBG;
	}

	/*Metodo que cria o subgrafo de contexto 1*/
	void createContextGraph1()
	{
		//Cria o cone azul
		ColoringAttributes ca1 = new ColoringAttributes();
		ca1.setColor(0.0f,0.0f,1.0f);
		Appearance appCone1 = new Appearance();
		appCone1.setColoringAttributes(ca1);
		Cone cone1 = new Cone(0.5f,3.0f, appCone1);
		
		//Cria a esfera vermelha
		ColoringAttributes ca2 = new ColoringAttributes();
		ca2.setColor(1.0f,0.0f,0.0f);
		Appearance appSphere1 = new Appearance();
		appSphere1.setColoringAttributes(ca2);
		Sphere sphere1 = new Sphere(1.0f, appSphere1);

		// Adiciona o cone azul ao seu branchGroup
		blueCone.addChild(cone1);
		// Adiciona a esfera vermelha ao seu branchGroup
		redSphere.addChild(sphere1);

		//Adiciona o cone azul e a esfera vermelha na cena1
		scene1.addChild(blueCone);
		scene1.addChild(redSphere);

		scene1.compile();
	}

	/*Metodo que cria o subgrafo de contexto 2*/
	void createContextGraph2()
	{
		//Cria o cone amarelo
		ColoringAttributes ca1 = new ColoringAttributes();
		ca1.setColor(1.0f,1.0f,0.0f);
		Appearance appCone2 = new Appearance();
		appCone2.setColoringAttributes(ca1);
		Cone cone2 = new Cone(0.5f,3.0f, appCone2);
		
		//Cria a esfera verde
		ColoringAttributes ca2 = new ColoringAttributes();
		ca2.setColor(0.0f,1.0f,0.0f);
		Appearance appSphere2 = new Appearance();
		appSphere2.setColoringAttributes(ca2);
		Sphere sphere2 = new Sphere(1.0f, appSphere2);

		// Adiciona o cone amarelo ao seu branchGroup
		yellowCone.addChild(cone2);
		// Adiciona a esfera verde ao seu branchGroup
		greenSphere.addChild(sphere2);

		//Adiciona o cone amarelo e a esfera verde na cena2
		scene2.addChild(yellowCone);
		scene2.addChild(greenSphere);

		scene2.compile();
	}

	/*Metodo que trata os eventos do mouse*/
	public void actionPerformed(ActionEvent e)
	{
		//Trata o botao da cena1 quando pressionado
		if (e.getSource()==button1)
		{
			//Verifica se a cena1 esta "viva"
			if (scene1.isLive())
			{
				//Remove a cena1
				locale.removeBranchGraph(scene1);
			}
			else
			{
				//Adiciona  a cena 1
				locale.addBranchGraph(scene1);
			}
		}
		//Trata o botao do cone da cena1 quando pressionado
		else if (e.getSource()==button2)//botao 2
		{
			//Verifica se a cena1 esta "viva"
			if (scene1.isLive())
			{
				//Verifica se o cone da cena1 esta "vivo"
				if (blueCone.isLive())
				{
					//Remove o cone azul
					blueCone.detach();
				}
				else
				{
					//Adiciona o cone azul
					scene1.addChild(blueCone);
				}
			}	
		}
		//Trata o botao da cena2 quando pressionado
		else if (e.getSource()==button3)//botao 3
		{
			//Verifica se a cena2 esta "viva"
			if (scene2.isLive())
			{
				//Remove a cena2
				locale.removeBranchGraph(scene2);
			}
			else
			{
				//Adiciona a cena2
				locale.addBranchGraph(scene2);
			}
		}
		//Trata o botao do cone da cena2 quando pressionado
		else if (e.getSource()==button4)//botao 4
		{
			//Verifica se a cena2 esta "viva"
			if (scene2.isLive())
			{
				//Verifica se o cone da cena2 esta "vivo"
				if (yellowCone.isLive())
				{
					//Remove o cone amarela
					yellowCone.detach();
				}
				else
				{
					//Adiciona o cone amarela
					scene2.addChild(yellowCone);
				}
			}
		}
	}

	/*Método principal*/
	public static void main(String[] args) 
	{
		new Lesson2_2();
	}
}