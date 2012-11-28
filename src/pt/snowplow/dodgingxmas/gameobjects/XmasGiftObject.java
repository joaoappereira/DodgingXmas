package pt.snowplow.dodgingxmas.gameobjects;

import pt.snowplow.framework.GameObject;
import pt.snowplow.framework.PhysicsComponent;
import pt.snowplow.framework.VisualComponent;

public class XmasGiftObject extends GameObject {
	
	public XmasGiftObject() {
		VisualComponent vc = new VisualComponent();
		addComponent(VisualComponent.class,vc);
		PhysicsComponent pc = new PhysicsComponent();
		addComponent(PhysicsComponent.class, pc);
	}

	public void init(Object sprite) {
		VisualComponent vc = (VisualComponent) getComponent(VisualComponent.class);
		vc.init(sprite);
	}
	
	@Override
	public void update() {
		super.update();
		PhysicsComponent pc = (PhysicsComponent) getComponent(PhysicsComponent.class);
		VisualComponent vc = (VisualComponent) this.getComponent(VisualComponent.class);
		vc.setPosition(pc.getPosition());
	}
}
