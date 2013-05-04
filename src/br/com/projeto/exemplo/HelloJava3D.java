package br.com.projeto.exemplo;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class HelloJava3D extends Applet{
	public HelloJava3D(){
		//Setando Layout
		setLayout(new BorderLayout());
		
		/* Define as configurações da Tela */
        GraphicsConfigTemplate3D g3d = new GraphicsConfigTemplate3D();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getBestConfiguration(g3d);

        // Iniciando Canvas
		Canvas3D canvas = new Canvas3D(gc);
		add("Center", canvas);
		
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
		TransformGroup objSpin = new TransformGroup();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRoot.addChild(objSpin);

		// Create a simple shape leaf node, add it to the scene graph.
		// ColorCube is a Convenience Utility class
		objSpin.addChild(new ColorCube(0.4));

		// create time varying function to drive the animation
		Alpha rotationAlpha = new Alpha(3, 2000);

		// Create a new Behavior object that performs the desired
		// operation on the specified transform object and add it into
		// the scene graph.
		RotationInterpolator rotator =
		new RotationInterpolator(rotationAlpha, objSpin);

		// a bounding sphere specifies a region a behavior is active
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		objSpin.addChild(rotator);
		return objRoot;
	}
	
	public static void main(String[] args){
		// Executamos a classe MainFrame passa a classe HelloJava3D e setamos a tamanho da janela
		Frame frame = new MainFrame(new HelloJava3D(),256,256);
	}
}
