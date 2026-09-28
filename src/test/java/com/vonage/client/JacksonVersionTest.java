package com.vonage.client;

import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.core.Version;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression test for VDLC-2305 / VDLC-2306: pom.xml has repeatedly flip-flopped between
 * vulnerable and patched jackson-databind releases. This asserts that the artifact actually
 * resolved on the classpath is not the vulnerable 2.19.0 release (or older).
 */
public class JacksonVersionTest {

	@Test
	public void resolvedJacksonDatabindVersionIsNotVulnerable() {
		Version version = PackageVersion.VERSION;
		assertFalse(version.isUnknownVersion(), "Could not determine jackson-databind version on classpath");

		boolean atLeastPatched = version.getMajorVersion() > 2
				|| (version.getMajorVersion() == 2 && version.getMinorVersion() >= 20);

		assertTrue(atLeastPatched, "jackson-databind " + version
				+ " is below the minimum patched version 2.20.0 (see VDLC-2305)");
	}
}
