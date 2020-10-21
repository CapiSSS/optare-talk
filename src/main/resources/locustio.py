import time
from locust import HttpUser, task, between

class QuickstartUser(HttpUser):
    wait_time = between(1, 2)

    """
    @task
    def view_item(self):
        for filmId in range(6):
            self.client.get(f"/starwars/films/{filmId + 1}/characters", name="/starwars/films")
            time.sleep(1)*/
	"""
    @task
    def view_time(self):
    	self.client.get(f"/time/America/Cayenne", name="/time")
    	time.sleep(1)
    	self.client.get(f"/time/Atlantic/Faroe", name="/time")
    	time.sleep(1)
    	self.client.get(f"/time/Pacific/Norfolk", name="/time")
    	time.sleep(1)
    	self.client.get(f"/time/Europe/Belgrade", name="/time")
    	time.sleep(1)
    	self.client.get(f"/time/Asia/Kamchatka", name="/time")
    	time.sleep(1)
    	self.client.get(f"/time/Africa/Lagos", name="/time")
    	time.sleep(1)