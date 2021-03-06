package org.pac4j.core.authorization.authorizer;

import org.junit.Before;
import org.junit.Test;
import org.pac4j.core.context.MockWebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.AnonymousProfile;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.TestsConstants;
import org.pac4j.core.util.TestsHelper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests {@link IsAnonymousAuthorizer}.
 *
 * @author Jerome Leleu
 * @since 1.9.0
 */
public final class IsAnonymousAuthorizerTests implements TestsConstants {

    private IsAnonymousAuthorizer authorizer;

    private List<CommonProfile> profiles;

    @Before
    public void setUp() {
        authorizer = new IsAnonymousAuthorizer();
        profiles = new ArrayList<>();
    }

    @Test
    public void testNoProfile() throws HttpAction {
        assertTrue(authorizer.isAuthorized(null, profiles));
    }

    @Test
    public void testAnonymousProfile() throws HttpAction {
        profiles.add(new AnonymousProfile());
        assertTrue(authorizer.isAuthorized(null, profiles));
    }

    @Test
    public void testAnonProfileTwoProfiles() throws HttpAction {
        profiles.add(new AnonymousProfile());
        profiles.add(new CommonProfile());
        assertFalse(authorizer.isAuthorized(null, profiles));
    }

    @Test
    public void testTwoAnonProfiles() throws HttpAction {
        profiles.add(new AnonymousProfile());
        profiles.add(new AnonymousProfile());
        assertTrue(authorizer.isAuthorized(null, profiles));
    }

    @Test
    public void testCommonProfile() throws HttpAction {
        profiles.add(new CommonProfile());
        assertFalse(authorizer.isAuthorized(null, profiles));
    }

    @Test
    public void testCommonProfileRedirectionUrl() {
        profiles.add(new CommonProfile());
        authorizer.setRedirectionUrl(PAC4J_URL);
        TestsHelper.expectException(() -> authorizer.isAuthorized(MockWebContext.create(), profiles), HttpAction.class, "user should be anonymous");
    }
}
