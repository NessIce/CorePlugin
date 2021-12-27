package me.ness.core.bukkit.account.friends;

public enum FriendStatus {

    PENDING_SENDING("Pendente Enviado"),
    PENDING_RECIVING("Pendente Recebido"),
    ACTIVE("Ativo"),
    BLOCK("Bloqueado");

    public String traduction;

    public String getTraduction() {
        return traduction;
    }

    FriendStatus(String traduction){
        this.traduction = traduction;
    }
}
