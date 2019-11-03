package com.herokuapp.theinternet.dropdowntests;

import com.herokuapp.theinternet.base.TestUtilities;
import com.herokuapp.theinternet.pages.DropDownPage;
import com.herokuapp.theinternet.pages.WelcomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DropDownTest extends TestUtilities {

    @Test
    public void optionTwoTest() {

        // open main page
        WelcomePage welcomePage = new WelcomePage(driver, log);
        welcomePage.openPage();

        // Click on Dropdown link
        DropDownPage dropdownPage = welcomePage.clickDropdownLink();

        // Select Option 2
        dropdownPage.selectOption(2);

        // Verify Option 2 is selected
        String selectedOption = dropdownPage.getSelectedOption();
        Assert.assertTrue(selectedOption.equals("Option 2"),
                "Option 2 is not selected. Instead selected - " + selectedOption);
    }
}
