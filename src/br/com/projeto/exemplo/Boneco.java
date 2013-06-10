package br.com.projeto.exemplo;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Boneco {

	public Boneco() {

		SimpleUniverse universo = new SimpleUniverse();
		BranchGroup group = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);

		Sphere esfera = new Sphere(0.25f);
		TransformGroup tg = new TransformGroup();
		Transform3D transform = new Transform3D();
		Vector3f vector = new Vector3f(.0f, .5f, .0f);
		transform.setTranslation(vector);
		tg.setTransform(transform);
		tg.addChild(esfera);

		Cone cone = new Cone(0.3f, 0.55f);
		TransformGroup tg2 = new TransformGroup();
		Transform3D transform2 = new Transform3D();
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

		TransformGroup tg4 = new TransformGroup();
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
		BoundingSphere limites = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100);
		Vector3f direcao = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight luz = new DirectionalLight(cor, direcao);
		luz.setInfluencingBounds(limites);
		group.addChild(luz);
		universo.getViewingPlatform().setNominalViewingTransform();
		universo.addBranchGraph(group);
	}

	public static void main(String[] args) {
		new Boneco();
	}

}
