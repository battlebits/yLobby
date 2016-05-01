package br.com.battlebits.ylobby.profile;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.battlebits.ycommon.common.BattlebitsAPI;
import br.com.battlebits.ycommon.common.permissions.enums.Group;
import br.com.battlebits.ylobby.yLobbyPlugin;

public class ProfileRanksInventory {

	private Inventory normalInventory;
	private Inventory lightInventory;
	private Inventory premiumInventory;
	private Inventory ultimateInventory;
	private Inventory youtuberInventory;
	private Inventory staffInventory;
	private ItemStack currentLight;
	private ItemStack noLight;
	private ItemStack hasLight;
	private ItemStack currentPremium;
	private ItemStack noPremium;
	private ItemStack hasPremium;
	private ItemStack currentUltimate;
	private ItemStack noUltimate;
	private ItemStack hasUltimate;
	private ItemStack noYoutuber;
	private ItemStack currentYoutuber;
	private ItemStack currentStaff;
	private ItemStack backToProfile;

	public ProfileRanksInventory() {
		currentLight = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta cLightMeta = currentLight.getItemMeta();
		cLightMeta.setDisplayName("§a§lLIGHT");
		cLightMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §averde §7e prefixo §a§lLIGHT §7no seu nome (Ex: §a§lLIGHT §aExemplo§7)" + "\\n" + "\\n§3§lEsquerdo§3 para usar esta §3§lTAG" + "\\n§b§lDireito§b para comprar um §b§lUPGRADE" + "\\n" + "\\n§9Seu §9§lGRUPO §9atual é §9§leste§9!"));
		currentLight.setItemMeta(cLightMeta);
		noLight = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta nLightMeta = currentLight.getItemMeta();
		nLightMeta.setDisplayName("§a§lLIGHT");
		nLightMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §averde §7e prefixo §a§lLIGHT §7no seu nome (Ex: §a§lLIGHT §aExemplo§7)" + "\\n" + "\\n§b§lClique§b para §b§lcomprar§b este §b§lRANK"));
		noLight.setItemMeta(nLightMeta);
		hasLight = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta hLightMeta = hasLight.getItemMeta();
		hLightMeta.setDisplayName("§a§lLIGHT");
		hLightMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §averde §7e prefixo §a§lLIGHT §7no seu nome (Ex: §a§lLIGHT §aExemplo§7)" + "\\n" + "\\n§3§lClique§3 para usar esta §3§lTAG" + "\\n" + "\\n§9Seu §9§lGRUPO §9atual é §9§lsuperior§9!"));
		hasLight.setItemMeta(hLightMeta);

		currentPremium = new ItemStack(Material.INK_SACK, 1, (short) 14);
		ItemMeta cPremiumMeta = currentLight.getItemMeta();
		cPremiumMeta.setDisplayName("§6§lPREMIUM");
		cPremiumMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §6dourada §7e prefixo §6§lPREMIUM §7no seu nome (Ex: §6§lPREMIUM §6Exemplo§7)" + "\\n" + "\\n§3§lEsquerdo§3 para usar esta §3§lTAG" + "\\n§b§lDireito§b para comprar um §b§lUPGRADE" + "\\n" + "\\n§9Seu §9§lGRUPO §9atual é §9§leste§9!"));
		currentPremium.setItemMeta(cPremiumMeta);
		noPremium = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta nPremiumMeta = currentLight.getItemMeta();
		nPremiumMeta.setDisplayName("§6§lPREMIUM");
		nPremiumMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §6dourada §7e prefixo §6§lPREMIUM §7no seu nome (Ex: §6§lPREMIUM §6Exemplo§7)" + "\\n" + "\\n§b§lClique§b para §b§lcomprar§b este §b§lRANK"));
		noPremium.setItemMeta(nPremiumMeta);
		hasPremium = new ItemStack(Material.INK_SACK, 1, (short) 14);
		ItemMeta hPremiumMeta = hasLight.getItemMeta();
		hPremiumMeta.setDisplayName("§6§lPREMIUM");
		hPremiumMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §6dourada §7e prefixo §6§lPREMIUM §7no seu nome (Ex: §6§lPREMIUM §6Exemplo§7)" + "\\n" + "\\n§3§lClique§3 para usar esta §3§lTAG" + "\\n" + "\\n§9Seu §9§lGRUPO §9atual é §9§lsuperior§9!"));
		hasPremium.setItemMeta(hPremiumMeta);

		currentUltimate = new ItemStack(Material.INK_SACK, 1, (short) 13);
		ItemMeta cUltimateMeta = currentUltimate.getItemMeta();
		cUltimateMeta.setDisplayName("§d§lULTIMATE");
		cUltimateMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §drosa §7e prefixo §d§lULTIMATE §7no seu nome (Ex: §d§lULTIMATE §dExemplo§7)" + "\\n- §3Double §3§lXP §7e §3Double §3§lMoney" + "\\n" + "\\n§3§lClique§3 para usar esta §3§lTAG" + "\\n" + "\\n§9Seu §9§lGRUPO §9atual é §9§leste§9!"));
		currentUltimate.setItemMeta(cUltimateMeta);
		noUltimate = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta nUltimateMeta = noUltimate.getItemMeta();
		nUltimateMeta.setDisplayName("§d§lULTIMATE");
		nUltimateMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §drosa §7e prefixo §d§lULTIMATE §7no seu nome (Ex: §d§lULTIMATE §dExemplo§7)" + "\\n- §3Double §3§lXP §7e §3Double §3§lMoney" + "\\n" + "\\n§b§lClique§b para §b§lcomprar§b este §b§lRANK"));
		noUltimate.setItemMeta(nUltimateMeta);
		hasUltimate = new ItemStack(Material.INK_SACK, 1, (short) 13);
		ItemMeta hUltimateMeta = hasUltimate.getItemMeta();
		hUltimateMeta.setDisplayName("§d§lULTIMATE");
		hUltimateMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §drosa §7e prefixo §d§lULTIMATE §7no seu nome (Ex: §d§lULTIMATE §dExemplo§7)" + "\\n- §3Double §3§lXP §7e §3Double §3§lMoney" + "\\n" + "\\n§3§lClique§3 para usar esta §3§lTAG" + "\\n" + "\\n§9Seu §9§lGRUPO §9atual é §9§lsuperior§9!"));
		hasUltimate.setItemMeta(hUltimateMeta);

		currentYoutuber = new ItemStack(Material.INK_SACK, 1, (short) 12);
		ItemMeta cYoutuberMeta = currentYoutuber.getItemMeta();
		cYoutuberMeta.setDisplayName("§b§lYOUTUBER");
		cYoutuberMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §bazul §7e prefixo §b§lYOUTUBER §7no seu nome (Ex: §b§lYOUTUBER §bExemplo§7)" + "\\n- §3Double §3§lXP §7e §3Double §3§lMoney" + "\\n" + "\\n§3§lClique§3 para usar esta §3§lTAG" + "\\n" + "\\n§9Seu §9§lGRUPO §9atual é §9§leste§9!"));
		currentYoutuber.setItemMeta(cYoutuberMeta);
		noYoutuber = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta nYoutuberMeta = noYoutuber.getItemMeta();
		nYoutuberMeta.setDisplayName("§b§lYOUTUBER");
		nYoutuberMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Vantagens em toda a Network:" + "\\n- Slots reservados em todos os servidores da rede BattleBits" + "\\n- Tag §bazul §7e prefixo §b§lYOUTUBER §7no seu nome (Ex: §b§lYOUTUBER §bExemplo§7)" + "\\n- §3Double §3§lXP §7e §3Double §3§lMoney" + "\\n" + "\\n§b§lClique§b para ver nosso §b§lTwitter§b!"));
		noYoutuber.setItemMeta(nYoutuberMeta);

		currentStaff = new ItemStack(Material.INK_SACK, 1, (short) 11);
		ItemMeta cStaffMeta = currentYoutuber.getItemMeta();
		cStaffMeta.setDisplayName("§e§lSTAFF");
		cStaffMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Voce atualmente faz parte da equipe do BattleBits Network! Nao importa o que voce seja builder ou admin, voce merece estar onde voce esta :D"));
		currentStaff.setItemMeta(cStaffMeta);

		backToProfile = new ItemStack(Material.ARROW, 1);
		ItemMeta backMeta = backToProfile.getItemMeta();
		backMeta.setDisplayName("§5§lPerfil");
		backMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("Clique aqui para voltar ao seu perfil."));
		backToProfile.setItemMeta(backMeta);

		normalInventory = Bukkit.createInventory(null, 27, "       §nSobre o seu grupo atual");
		lightInventory = Bukkit.createInventory(null, 27, "       §nSobre o seu grupo atual");
		premiumInventory = Bukkit.createInventory(null, 27, "       §nSobre o seu grupo atual");
		ultimateInventory = Bukkit.createInventory(null, 27, "       §nSobre o seu grupo atual");
		youtuberInventory = Bukkit.createInventory(null, 27, "       §nSobre o seu grupo atual");
		staffInventory = Bukkit.createInventory(null, 27, "       §nSobre o seu grupo atual");

		normalInventory.setItem(10, noLight);
		normalInventory.setItem(12, noPremium);
		normalInventory.setItem(14, noUltimate);
		normalInventory.setItem(16, noYoutuber);
		normalInventory.setItem(22, backToProfile);

		lightInventory.setItem(10, currentLight);
		lightInventory.setItem(12, noPremium);
		lightInventory.setItem(14, noUltimate);
		lightInventory.setItem(16, noYoutuber);
		lightInventory.setItem(22, backToProfile);

		premiumInventory.setItem(10, hasLight);
		premiumInventory.setItem(12, currentPremium);
		premiumInventory.setItem(14, noUltimate);
		premiumInventory.setItem(16, noYoutuber);
		premiumInventory.setItem(22, backToProfile);

		ultimateInventory.setItem(10, hasLight);
		ultimateInventory.setItem(12, hasPremium);
		ultimateInventory.setItem(14, currentUltimate);
		ultimateInventory.setItem(16, noYoutuber);
		ultimateInventory.setItem(22, backToProfile);

		youtuberInventory.setItem(10, hasLight);
		youtuberInventory.setItem(12, hasPremium);
		youtuberInventory.setItem(14, hasUltimate);
		youtuberInventory.setItem(16, currentYoutuber);
		youtuberInventory.setItem(22, backToProfile);

		staffInventory.setItem(13, currentStaff);
		staffInventory.setItem(22, backToProfile);
	}

	public void open(Player p) {
		Group group = BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId()).getServerGroup();
		if (group == Group.NORMAL) {
			p.openInventory(normalInventory);
		} else if (group == Group.LIGHT) {
			p.openInventory(lightInventory);
		} else if (group == Group.PREMIUM) {
			p.openInventory(premiumInventory);
		} else if (group == Group.ULTIMATE) {
			p.openInventory(ultimateInventory);
		} else if (group == Group.YOUTUBER) {
			p.openInventory(youtuberInventory);
		} else {
			p.openInventory(staffInventory);
		}
	}

}
