package jobs;

import models.Restaurant;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
	@Override
	public void doJob() throws Exception {
		if (Restaurant.findAll().isEmpty()) {
			Fixtures.loadModels("initial-data.yml");
		}
	}
}
