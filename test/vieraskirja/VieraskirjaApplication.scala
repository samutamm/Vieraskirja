package vieraskirja

import play.api.test.{Helpers, FakeApplication, WithApplication}

class VieraskirjaApplication extends WithApplication(FakeApplication(additionalConfiguration = Helpers.inMemoryDatabase()))