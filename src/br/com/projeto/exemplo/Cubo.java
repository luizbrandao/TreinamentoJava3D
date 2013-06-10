package br.com.projeto.exemplo;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.j3d.Alpha;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

@SuppressWarnings("serial")
public class Cubo extends Applet implements MouseMotionListener, MouseListener{
	
	TransformGroup objSpin;
	Transform3D trans;
	Canvas3D canvas;
	ColorCube cube;
	Point3f pontos = new Point3f();
	
	public Cubo(){
		//Setando Layout
		setLayout(new BorderLayout());
		
		/* Define as configurações da Tela */
        GraphicsConfigTemplate3D g3d = new GraphicsConfigTemplate3D();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getBestConfiguration(g3d);

        // Iniciando Canvas
		canvas = new Canvas3D(gc);
		add("Center", canvas);
		
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		
		// Criando a cena
		BranchGroup scene = createSceneGraph();
		scene.compile();
		
		//Criacao do Universe
		SimpleUniverse universe = new SimpleUniverse(canvas);
		
		// Setando as configuracoes padroes do universo
		universe.getViewingPlatform().setNominalViewingTransform();
		
		// Adicionando a cena no universo 
		universe.addBranchGraph(scene);
	}
	
	public BranchGroup createSceneGraph(){
		// Create the root of the branch graph
		BranchGroup objRoot = new BranchGroup();
		
		
		// Create the transform group node and initialize it to the
		// identity. Add it to the root of the subgraph.
		objSpin = new TransformGroup();
		trans = new Transform3D();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objSpin.setTransform(trans);
				
		objRoot.addChild(objSpin);
		
		// Create a simple shape leaf node, add it to the scene graph.
		// ColorCube is a Convenience Utility class
		cube = new ColorCube(0.1);
		objSpin.addChild(cube);

		// a bounding sphere specifies a region a behavior is active
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
        TextureLoader texturaBg = new TextureLoader(toolkit.getImage("C:\\Users\\Luiz Brandão\\Documents\\NetBeansProjects\\FirstApplication\\src\\firstaplication\\teste.png"), this);
        Background bg = new Background(texturaBg.getImage());
		bg.setApplicationBounds(bounds);		
		objRoot.addChild(bg);
		
		MouseTranslate mt = new MouseTranslate(objSpin);
        mt.setSchedulingBounds(bounds);
        objRoot.addChild(mt);

        MouseRotate mr = new MouseRotate(objSpin);
        mr.setSchedulingBounds(bounds);
        objRoot.addChild(mr);

        MouseWheelZoom mwz = new MouseWheelZoom(objSpin);
        mwz.setSchedulingBounds(bounds);
        objRoot.addChild(mwz);
        
		return objRoot;
	}
	
	public static void main(String[] args){
		// Executamos a classe MainFrame passa a classe HelloJava3D e setamos a tamanho da janela
		Frame frame = new MainFrame(new Cubo(),256,256);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		getDados();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		getDados();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public void getDados(){
		cube.getLocalToVworld(trans);
		trans.transform(pontos);
		System.out.println("x="+pontos.x+" y="+pontos.y+" z="+pontos.z);
		pontos = new Point3f();
	}
}
