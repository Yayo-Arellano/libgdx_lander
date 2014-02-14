package com.tiarsoft.lander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tiarsoft.lander.game.objetos.Bomba;
import com.tiarsoft.lander.game.objetos.Estrella;
import com.tiarsoft.lander.game.objetos.Gas;
import com.tiarsoft.lander.game.objetos.Nave;
import com.tiarsoft.lander.game.objetos.Plataforma;

public class Colisiones implements ContactListener {

	WorldGame oWorld;

	public Colisiones(WorldGame oWorld) {
		this.oWorld = oWorld;
	}

	@Override
	public void beginContact(Contact contact) {

		if (contact.getFixtureA().getBody().getUserData() instanceof Nave)
			beginContactNaveOtraCosa(contact.getFixtureA(), contact.getFixtureB());
		else if (contact.getFixtureB().getBody().getUserData() instanceof Nave)
			beginContactNaveOtraCosa(contact.getFixtureB(), contact.getFixtureA());

		else if (contact.getFixtureB().getBody().getUserData() instanceof Bomba)
			beginContactBombaOtraCosa(contact.getFixtureB(), contact.getFixtureA());

		else if (contact.getFixtureA().getBody().getUserData() instanceof Bomba)
			beginContactBombaOtraCosa(contact.getFixtureA(), contact.getFixtureB());

	}

	public void beginContactNaveOtraCosa(Fixture nave, Fixture otraCosa) {
		Body bodyNave = nave.getBody();
		Nave oNave = (Nave) bodyNave.getUserData();

		Body bodyOtraCosa = otraCosa.getBody();
		Object oOtraCosa = bodyOtraCosa.getUserData();

		if (oOtraCosa instanceof Gas) {
			Gas obj = (Gas) oOtraCosa;

			if (obj.state == Gas.STATE_NORMAL) {
				oNave.gas += 100;
				obj.state = Gas.STATE_TOMADA;
			}
			return;
		}
		else if (oOtraCosa instanceof Estrella) {
			Estrella obj = (Estrella) oOtraCosa;

			if (obj.state == Estrella.STATE_NORMAL) {
				oWorld.estrellasTomadas++;
				obj.state = Estrella.STATE_TOMADA;
			}
			return;
		}
		else if (oOtraCosa instanceof Bomba) {
			Bomba obj = (Bomba) oOtraCosa;
			if (obj.state == Bomba.STATE_NORMAL) {
				obj.state = Bomba.STATE_TOMADA;
				oNave.colision(5);
				Vector2 blastDirection = bodyNave.getWorldCenter().sub(bodyOtraCosa.getWorldCenter());
				blastDirection.nor();
				bodyNave.applyLinearImpulse(blastDirection.scl(2f), bodyNave.getWorldCenter(), true);
			}
			return;
		}

		Vector2 vel = bodyNave.getLinearVelocity().sub(bodyOtraCosa.getLinearVelocity());
		float velocidadResultante = (float) Math.sqrt(vel.x * vel.x + vel.y * vel.y);
		if (velocidadResultante > 1 || velocidadResultante < -1) {
			oNave.colision(velocidadResultante);
			Gdx.app.log("Velocidad Muerte X", vel.x + "");
			Gdx.app.log("Velocidad Muerte Y", vel.y + "");
			Gdx.app.log("Velocidad Muerte Res", velocidadResultante + "");
		}

		if (oOtraCosa instanceof Plataforma)
			if (((Plataforma) oOtraCosa).isFinal)
				oWorld.state = WorldGame.STATE_NEXT_LEVEL;

	}

	private void beginContactBombaOtraCosa(Fixture bomba, Fixture otraCosa) {
		Body bodyBomba = bomba.getBody();
		Bomba oBomba = (Bomba) bodyBomba.getUserData();

		Body bodyOtraCosa = otraCosa.getBody();
		Object oOtraCosa = bodyOtraCosa.getUserData();

		if (oOtraCosa.equals("pared")) {

		}
		oBomba.cambioDireccion();

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

		if (contact.getFixtureA().getBody().getUserData() instanceof Nave)
			preSolveNaveOtraCosa(contact.getFixtureA(), contact.getFixtureB());
		else if (contact.getFixtureB().getBody().getUserData() instanceof Nave)
			preSolveNaveOtraCosa(contact.getFixtureB(), contact.getFixtureA());
	}

	public void preSolveNaveOtraCosa(Fixture nave, Fixture otraCosa) {
		Body bodyNave = nave.getBody();
		Nave oNave = (Nave) bodyNave.getUserData();

		Body bodyOtraCosa = otraCosa.getBody();
		Object oOtraCosa = bodyOtraCosa.getUserData();

		if (oOtraCosa instanceof Bomba) {
			// nave.setRestitution(1);

			return;
		}

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		if (contact.getFixtureA().getBody().getUserData() instanceof Nave)
			postSolveNaveOtraCosa(contact.getFixtureA(), contact.getFixtureB());
		else if (contact.getFixtureB().getBody().getUserData() instanceof Nave)
			postSolveNaveOtraCosa(contact.getFixtureB(), contact.getFixtureA());
	}

	public void postSolveNaveOtraCosa(Fixture nave, Fixture otraCosa) {
		Body bodyNave = nave.getBody();
		Nave oNave = (Nave) bodyNave.getUserData();

		Body bodyOtraCosa = otraCosa.getBody();
		Object oOtraCosa = bodyOtraCosa.getUserData();

		if (oOtraCosa instanceof Bomba) {
			otraCosa.setSensor(true);
			return;
		}

	}

}
