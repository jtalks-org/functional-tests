package org.jtalks.tests.jcommune.webdriver.exceptions;

/** @author stanislav bashkirtsev */
public class CouldNotOpenBranchException extends Exception {
    public CouldNotOpenBranchException(String branchName) {
        super("Could not open branch: [" + branchName + "]");
    }
}
