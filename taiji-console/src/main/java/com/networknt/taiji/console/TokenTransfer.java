package com.networknt.taiji.console;

public class TokenTransfer extends WalletManager {
    public TokenTransfer() {
    }

    public TokenTransfer(IODevice console) {
        super(console);
    }

    public static void main(String[] args) {
        new TokenTransfer().run();
    }

    static void main(IODevice console) {
        new TokenTransfer(console).run();
    }

    private void run() {

    }
}
