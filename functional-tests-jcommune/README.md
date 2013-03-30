####Test Methods:
* All test cases are kept in separate test methods. To simplify future debugging we're not using `@DataProvider`
* Test methods should be named \[description\]_\[TestCaseId\], e.g.: `emptyUsernameIsNotLoggedIn_TC1234()`.
* Methods should be marked with groups `@Test(groups = {"sanity", "primary", "regression"})` depending on the type of
 test. These groups are going to be run separately.