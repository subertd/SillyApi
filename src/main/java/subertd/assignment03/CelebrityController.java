package subertd.assignment03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class CelebrityController {

    private static final Logger topLogger = Logger.getLogger("");

    private static final Logger logger =
            Logger.getLogger(CelebrityController.class.getName());

    private CelebrityService celebrityService;

    static {
        //topLogger.setLevel(Level.FINEST);
    }

    @Autowired
    public void setCelebrityService(final CelebrityService celebrityService) {
        logger.entering(CelebrityController.class.getName(),
                "setCelebrityService("
                        + String.valueOf(celebrityService)
                        + ")");
        this.celebrityService = celebrityService;
    }

    @RequestMapping(value = "/addCelebrity", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Celebrity addCelebrity(@RequestBody Celebrity celebrity) {
        logger.entering(CelebrityController.class.getName(),
                "addCelebrity(" + celebrity + ")");
        return celebrityService.addCelebrity(celebrity);
    }

    @RequestMapping(value = "/celebrity/IMDb_ID/{IMDb_ID}",
            method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Celebrity celebrityByImdbId(@PathVariable("IMDb_ID") String imdbId) {
        logger.entering(CelebrityController.class.getName(),
                "celebrityByImdbId(" + imdbId + ")");
        return celebrityService.getCelebrityByImdbId(imdbId);
    }

    @RequestMapping(value = "/celebrity/name/{name}",
            method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Celebrity> celebritiesByName(@PathVariable("name") String name)
    {
        logger.entering(CelebrityController.class.getName(),
                "celebritiesByName(" + name + ")");
        return celebrityService.getCelebritiesByName(name);
    }

    @RequestMapping(value = "/addSighting/{IMDb_ID}",
            method = RequestMethod.PUT, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public Celebrity addSighting(@PathVariable("IMDb_ID") String imdbId,
                            @RequestBody Sighting sighting) {
        logger.entering(CelebrityController.class.getName(),
                "addSighting(" + sighting + ")");
        return celebrityService.addSighting(imdbId, sighting);
    }

    @RequestMapping(value = "/celebrities/location",
        method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Celebrity> celebritsByLocation(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude
    ){
        logger.entering(CelebrityController.class.getName(),
                "celebritySightingsByLocation("
            + latitude + ", " + longitude);

        return celebrityService.getCelebritiesByLocation(
                latitude, longitude);
    }

    @RequestMapping(value = "/sightings/location",
            method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<CelebritySighting> celebritySightingsByLocation(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude
    ){
        logger.entering(CelebrityController.class.getName(),
                "celebritySightingsByLocation("
                        + latitude + ", " + longitude);

        return celebrityService.getCelebritySightingsByLocation(
                latitude, longitude);
    }
}