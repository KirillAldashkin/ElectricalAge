package mods.eln.electricalalarm;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import mods.eln.Eln;
import mods.eln.item.ThermalIsolatorElement;
import mods.eln.misc.IFunction;
import mods.eln.misc.Obj3D;
import mods.eln.misc.Obj3D.Obj3DPart;
import mods.eln.misc.Utils;
import mods.eln.node.SixNodeDescriptor;
import mods.eln.sim.DiodeProcess;
import mods.eln.sim.ThermalLoad;
import mods.eln.sim.ThermalLoadInitializer;

import com.google.common.base.Function;


public class ElectricalAlarmDescriptor extends SixNodeDescriptor{


	public ElectricalAlarmDescriptor(
			String name,
			Obj3D obj,
			int light,
			String soundName,double soundTime,float soundLevel
			) {
		super(name, ElectricalAlarmElement.class,ElectricalAlarmRender.class);
		this.obj = obj;
		this.soundName = soundName;
		this.soundTime = soundTime;
		this.soundLevel = soundLevel;
		this.light = light;
		if(obj != null){
			main = obj.getPart("main");
			rot = obj.getPart("rot");
			onTexture = obj.getAlternativeTexture(obj.getString("onTexture"));
			offTexture = obj.getAlternativeTexture(obj.getString("offTexture"));
			if(rot != null){
				rotSpeed = rot.getFloat("speed");
			}
		}
	}
	int light;
	Obj3D obj;
	Obj3DPart main,rot;
	
	ResourceLocation onTexture,offTexture;
	String soundName;
	double soundTime;
	float soundLevel;
	public float rotSpeed = 0f;
	
	void draw(boolean warm,float rotAlpha)
	{
		if(warm) Utils.bindTexture(onTexture);
		else	Utils.bindTexture(offTexture);
		if(main != null) main.drawNoBind();
		if(rot != null){
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			if(warm) Utils.disableLight();
			else GL11.glDisable(GL11.GL_LIGHTING);
			rot.drawNoBind(rotAlpha,1f,0f,0f);
			if(warm) Utils.enableLight();
			else GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}
	
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer,
			List list, boolean par4) {
		// TODO Auto-generated method stub
		super.addInformation(itemStack, entityPlayer, list, par4);
		list.add("Emit a sonor alarm when");
		list.add("the input signal is high");
	}
}
