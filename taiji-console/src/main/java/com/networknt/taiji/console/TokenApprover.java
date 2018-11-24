package com.networknt.taiji.console;

public class TokenApprover extends WalletManager {
    public TokenApprover() {
    }

    public TokenApprover(IODevice console) {
        super(console);
    }

    public static void main(String[] args) {
        new TokenApprover().run();
    }

    static void main(IODevice console) {
        new TokenApprover(console).run();
    }

    private void run() {

    }
}
