package models;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;
import securesocial.provider.ProviderType;
import securesocial.provider.UserId;

public class UserSocialInformationTest extends UnitTest {
    
    @Before
    public void setUp() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("data.yml");
    }
    
    @Test
    public void testGetUserSocial() {
        UserId uid = new UserId(); 
        uid.id = "miId";
        uid.provider = ProviderType.google;
        
        UserSocialInformation usi = UserSocialInformation.findByUserId(uid);
        assertNotNull(usi);
        assertEquals("gonto@gonto.com", usi.user.mail);
    }
    
    @Test
    public void testGetUserSocialNotFound() {
        UserId uid = new UserId(); 
        uid.id = "miIdLalalo";
        uid.provider = ProviderType.google;
        
        UserSocialInformation usi = UserSocialInformation.findByUserId(uid);
        assertNull(usi);
    }
    
    

}
