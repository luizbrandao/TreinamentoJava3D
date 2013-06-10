package br.com.projeto.exemplo;

/********************* BIBLIOTECAS UTILIZADAS **********************/
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.media.j3d.*;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.*;
import javax.vecmath.*;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

public class LessonData extends JFrame implements MouseMotionListener {
	/*---------------------------- ATRIBUTOS -------------------------*/
	/* Universo Virtual */
	private VirtualUniverse universe;

	/* Ponto de referência no mundo virtual - localização dos objetos) */
	private Locale locale;

	/* Objeto que faz a ligação entre o universo virtual e a janela */
	private Canvas3D canvas3D;

	/* Objetos que fazem parte do subgrafo de contexto */
	private BranchGroup objRoot;
	private ColorCube colorCube;
	TransformGroup tg4;
	Transform3D td;
	TransformGroup tg2;
	Transform3D transform2;
	Point3f ponto = new Point3f();
	Cone cone;
	/*
	 * Variável que controla o estilo da tela: true (modo FullScreen), false
	 * (modo janela)
	 */
	boolean screenMode = false;

	/*--------------------------- CONSTRUTOR --------------------------*/
	public LessonData() {
		/************************ PROGRAMAÇÃO JAVA **********************/

		/* Utilização de um objeto Dimension para obter as configurações da tela */
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		/* Ajusta o tamanho da janela */
		this.setSize(dimension.width, dimension.height);

		/* Retira as bordas da janela, criando o modo FullScreen */
		if (screenMode) {
			this.setUndecorated(true);
		} else {
			this.setTitle("API Java3D – Primeiro Programa");
		}

		/************************* PROGRAMAÇÃO EM JAVA3D ****************/

		/* Cria o universo virtual */
		universe = new VirtualUniverse();

		/* Cria o Locale e anexa-o ao mundo virtual */
		locale = new Locale(universe);

		/* Cria o objeto canvas3D */
		GraphicsConfigTemplate3D g3d = new GraphicsConfigTemplate3D();
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		GraphicsConfiguration gcn = defaultScreen.getBestConfiguration(g3d);

		canvas3D = new Canvas3D(gcn);
		/* Ajusta a cor do canvas3D */
		canvas3D.setBackground(Color.black);
		/* Ajusta a dimensao do canvas 3D (mesmo tamanho da janela) */
		canvas3D.setSize(dimension.width, dimension.height);

		/* Adicionando o subgrafo de visualização */
		locale.addBranchGraph(createViewGraph());

		/* adicionando o subrafo de contexto */
		locale.addBranchGraph(createContextGraph());

		/************************** PROGRAMAÇÃO JAVA ********************/

		/* Adiciona o canvas3D ao tratador de eventos do teclado */
		canvas3D.addMouseMotionListener(this);

		/* Adiciona o canvas3D a janela e o exibe */
		this.getContentPane().add(canvas3D);
		this.show();
	}

	/*--------------------------- METODOS -----------------------------*/

	/* Método que cria o subgrafo de visualização */
	BranchGroup createViewGraph() {
		/* Cria um objeto BranchGroup */
		BranchGroup viewPlatformBG = new BranchGroup();

		/* Cria um objeto View */
		View view = new View();

		/* Cria um objeto ViewPlataform */
		ViewPlatform viewPlatform = new ViewPlatform();

		/* Cria um objeto PhysicalBody */
		PhysicalBody physicalBody = new PhysicalBody(new Point3d(0, 0, 0),
				new Point3d(0, 0, 0));

		/* Cria um objeto PhysicalEnvironment */
		PhysicalEnvironment physicalEnvironment = new PhysicalEnvironment();

		/* Adiciona vewPlatform ao view */
		view.attachViewPlatform(viewPlatform);
		/* Adiciona physicalBody ao view */
		view.setPhysicalBody(physicalBody);
		/* Adiciona physicalEnvironment ao view */
		view.setPhysicalEnvironment(physicalEnvironment);
		/* Adiciona canvas ao view */
		view.addCanvas3D(canvas3D);

		/* Cria um objeto Transform3D */
		Transform3D transform3D = new Transform3D();
		/* Configura o Transform3D com o Vector3f */
		transform3D.set(new Vector3f(0.0f, 0.0f, 10.0f));
		/* Cria um objeto TransformGroup */
		TransformGroup viewPlatformTG = new TransformGroup(transform3D);
		/* Adiciona o ViewPlatform ao TransformGroup */
		viewPlatformTG.addChild(viewPlatform);

		/* Adiciona o TransformGroup ao BranchGroup */
		viewPlatformBG.addChild(viewPlatformTG);

		/* Retorna o BranchGroup contendo o subgrafo de visualização */
		return viewPlatformBG;
	}

	/* Metodo que cria o subgrafo de contexto */
	BranchGroup createContextGraph() {
		BranchGroup group = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		Sphere esfera = new Sphere(0.25f);
		TransformGroup tg = new TransformGroup();
		Transform3D transform = new Transform3D();
		Vector3f vector = new Vector3f(.0f, .5f, .0f);
		transform.setTranslation(vector);
		tg.setTransform(transform);
		tg.addChild(esfera);

		cone = new Cone(0.3f, 0.55f);
		tg2 = new TransformGroup();
		transform2 = new Transform3D();
		Vector3f vector2 = new Vector3f(.0f, .05f, .0f);
		transform2.setTranslation(vector2);
		tg2.setTransform(transform2);
		tg2.addChild(cone);

		Cylinder cilindro = new Cylinder(0.05f, 0.3f);
		TransformGroup tg3 = new TransformGroup();
		Transform3D transform3 = new Transform3D();
		Vector3f vector3 = new Vector3f(.1f, -.4f, .0f);
		transform3.setTranslation(vector3);
		tg3.setTransform(transform3);
		tg3.addChild(cilindro);

		Cylinder cilindro2 = new Cylinder(0.05f, 0.3f);
		TransformGroup tg5 = new TransformGroup();
		Transform3D transform5 = new Transform3D();
		Vector3f vector5 = new Vector3f(-.1f, -.4f, .0f);
		transform5.setTranslation(vector5);
		tg5.setTransform(transform5);
		tg5.addChild(cilindro2);

		Cylinder cilindro3 = new Cylinder(0.05f, 0.3f);
		TransformGroup tg6 = new TransformGroup();
		Transform3D transform6 = new Transform3D();
		Vector3f vector6 = new Vector3f(-.28f, .1f, .0f);
		transform6.setTranslation(vector6);
		transform6.setRotation(new AxisAngle4f(0f, 0f, 1.0f, 1.3f));
		tg6.setTransform(transform6);
		tg6.addChild(cilindro3);

		Cylinder cilindro4 = new Cylinder(0.05f, 0.3f);
		TransformGroup tg7 = new TransformGroup();
		Transform3D transform7 = new Transform3D();
		Vector3f vector7 = new Vector3f(.28f, .1f, .0f);
		transform7.setTranslation(vector7);
		transform7.setRotation(new AxisAngle4f(0f, 0f, 1.0f, -1.3f));
		tg7.setTransform(transform7);
		tg7.addChild(cilindro4);

		tg4 = new TransformGroup();
		td = new Transform3D();
		tg4.setTransform(td);
		tg4.addChild(tg2);
		tg4.addChild(tg);
		tg4.addChild(tg3);
		tg4.addChild(tg5);
		tg4.addChild(tg6);
		tg4.addChild(tg7);
		group.addChild(tg4);

		tg4.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg4.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		KeyNavigatorBehavior keyNav = new KeyNavigatorBehavior(tg4);
		keyNav.setSchedulingBounds(bounds);
		group.addChild(keyNav);

		MouseTranslate mt = new MouseTranslate(tg4);
		mt.setSchedulingBounds(bounds);
		group.addChild(mt);

		MouseRotate mr = new MouseRotate(tg4);
		mr.setSchedulingBounds(bounds);
		group.addChild(mr);

		MouseWheelZoom mwz = new MouseWheelZoom(tg4);
		mwz.setSchedulingBounds(bounds);
		group.addChild(mwz);

		Color3f cor = new Color3f(.1f, 1.4f, .1f);
		BoundingSphere limites = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),	100);
		Vector3f direcao = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight luz = new DirectionalLight(cor, direcao);
		luz.setInfluencingBounds(limites);
		group.addChild(luz);
		return group;
	}

	/* Método principal */
	public static void main(String[] args) {
		new LessonData();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		cone.getLocalToVworld(td);
		td.transform(ponto);
		System.out.println("x="+ponto.x+" y="+ponto.y+" z="+ponto.z);
		ponto = new Point3f();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
}
