package com.lilithsthrone.game.sex.managers;

import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.CoverableArea;
import com.lilithsthrone.game.character.npc.dominion.DominionAlleywayAttacker;
import com.lilithsthrone.game.character.npc.dominion.DominionSuccubusAttacker;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.OrificeType;
import com.lilithsthrone.game.sex.Sex;
import com.lilithsthrone.game.sex.SexPace;
import com.lilithsthrone.game.sex.SexPositionType;
import com.lilithsthrone.game.sex.SexPositionSlot;
import com.lilithsthrone.game.sex.sexActions.SexActionInterface;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Colour;

/**
 * @since 0.1.0
 * @version 0.1.97
 * @author Innoxia
 */
public interface SexManagerInterface {

	public SexPositionType getPosition();
	
	public Map<GameCharacter, SexPositionSlot> getDominants();
	public Map<GameCharacter, SexPositionSlot> getSubmissives();
	
	
	public default SexPace getStartingSexPaceModifier(GameCharacter character) {
		return null;
	}
	
	/**
	 * @param character
	 * @return The SexPace that this character should be locked into for the duration of this sex scene.
	 */
	public default SexPace getForcedSexPace(GameCharacter character) {
		return null;
	}
	
	public default boolean isPlayerDom() {
		return getDominants().containsKey(Main.game.getPlayer());
	}
	
	/**
	 * @param sexActionPlayer The action that the player just took before the partner's turn.
	 * @return The action that the partner takes.
	 */
	public SexActionInterface getPartnerSexAction(SexActionInterface sexActionPlayer);
	
	
	public default String getStartSexDescription() {
		return "";
	}

	public default boolean isPlayerAbleToSwapPositions() {
		return true;
	}
	
	public default boolean isPlayerAbleToStopSex() {
		return Sex.isDom(Main.game.getPlayer())
				|| (Sex.isSubHasEqualControl()
					&& !(Sex.getActivePartner() instanceof DominionAlleywayAttacker) //TODO
					&& !(Sex.getActivePartner() instanceof DominionSuccubusAttacker));
	}
	
	public default boolean isAbleToRemoveSelfClothing(GameCharacter character){
		return true;
	}
	
	public default boolean isAbleToRemoveOthersClothing(GameCharacter character){
		if(character.isPlayer()) {
			return true;
		}
		return getDominants().containsKey(character) || Sex.isSubHasEqualControl();
	}
	
	public default boolean isItemUseAvailable() {
		return true;
	}
	
	public default boolean isPlayerStartNaked() {
		return false;
	}

	public default boolean isPartnerStartNaked() {
		return false;
	}
	
	public default boolean isPartnerUsingForeplayActions() {
		return true;
	}
	
	public default boolean isPublicSex() {
		return false;
	}
	
	public default String getRandomPublicSexDescription() {
		return "<p style='color:"+Colour.BASE_ORANGE.toWebHexString()+"; font-style:italic; text-align:center;'>"
					+UtilText.returnStringAtRandom(
							"The crowd of onlookers laugh and cheer as they look on.",
							"You hear someone in the crowd wolf-whistling as they watch you having sex.",
							"A pair of Enforcers shove their way through the crowd, but instead of putting a stop to your fun, they join the onlookers in laughing and commenting on your performance.",
							"You hear the crowd that's gathered to watch you commenting on your performance.",
							"You hear the crowd that's gathered to watch you commenting on [npc.name]'s performance.",
							"Cheering and laughing, the crowd of onlookers watch as you continue having sex with [npc.name].",
							"You glance across to see several members of the crowd touching themselves as they watch you and [npc.name] go at it.",
							"The crowd cheers you on as you and [npc.name] carry on having sex in front of them.",
							"The crowd laughs and cheers as you and [npc.name] carry on having sex in front of them.",
							"Several members of the crowd shout and cheer as you and [npc.name] carry on having sex in front of them.",
							"Several members of the crowd cheer you on as you and [npc.name] carry on having sex in front of them.")
				+"</p>";
	}
	
	public Map<GameCharacter, List<OrificeType>> getOrificesBannedMap();
	
	// Revealing CoverableAreas:

	// Player:
	public default String getPlayerAssRevealReaction() {
		return Sex.getActivePartner().getAssRevealDescription(Main.game.getPlayer());
	}

	public default String getPlayerVaginaRevealReaction() {
		return Sex.getActivePartner().getVaginaRevealDescription(Main.game.getPlayer());
	}

	public default String getPlayerBreastsRevealReaction() {
		return Sex.getActivePartner().getBreastsRevealDescription(Main.game.getPlayer());
	}

	public default String getPlayerPenisRevealReaction() {
		return Sex.getActivePartner().getPenisRevealDescription(Main.game.getPlayer());
	}

	public default String getPlayerMoundRevealReaction() {
		return Sex.getActivePartner().getMoundRevealDescription(Main.game.getPlayer());
	}

	// Partner:
	public default String getPartnerAssRevealReaction() {
		Sex.getActivePartner().getPlayerKnowsAreas().add(CoverableArea.ANUS);
		
		String s = "<p>"
				+ UtilText.parse(Sex.getActivePartner(), Sex.getActivePartner().getAssDescription())
				+ "</p>"
				+ Sex.getActivePartner().getAssRevealDescription(Sex.getActivePartner());
		
		return s;
	}

	public default String getPartnerBreastsRevealReaction() {
		Sex.getActivePartner().getPlayerKnowsAreas().add(CoverableArea.NIPPLES);
		
		String s = "<p>"
				+ UtilText.parse(Sex.getActivePartner(), Sex.getActivePartner().getBreastDescription())
				+ "</p>"
				+ Sex.getActivePartner().getBreastsRevealDescription(Sex.getActivePartner());
		
		return s;
	}

	public default String getPartnerPenisRevealReaction() {
		Sex.getActivePartner().getPlayerKnowsAreas().add(CoverableArea.PENIS);
		
		String s = "<p>"
				+ UtilText.parse(Sex.getActivePartner(), Sex.getActivePartner().getPenisDescription())
				+ "</p>"
				+ Sex.getActivePartner().getPenisRevealDescription(Sex.getActivePartner());
		
		return s;
	}

	public default String getPartnerVaginaRevealReaction() {
		Sex.getActivePartner().getPlayerKnowsAreas().add(CoverableArea.VAGINA);
		
		String s = "<p>"
				+ UtilText.parse(Sex.getActivePartner(), Sex.getActivePartner().getVaginaDescription())
				+ "</p>"
				+ Sex.getActivePartner().getVaginaRevealDescription(Sex.getActivePartner());
		
		return s;
	}

	public default String getPartnerMoundRevealReaction() {
		Sex.getActivePartner().getPlayerKnowsAreas().add(CoverableArea.PENIS);
		Sex.getActivePartner().getPlayerKnowsAreas().add(CoverableArea.VAGINA);
		
		String s = "<p>"
				+ UtilText.parse(Sex.getActivePartner(), Sex.getActivePartner().getMoundDescription())
				+ "</p>"
				+ Sex.getActivePartner().getMoundRevealDescription(Sex.getActivePartner());
		
		return s;
	}
}
