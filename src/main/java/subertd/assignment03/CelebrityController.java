package subertd.assignment03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class CelebrityController {

    private static final Logger logger =
            Logger.getLogger(CelebrityController.class.getName());

    private CelebrityService celebrityService;

    @Autowired
    public void setCelebrityService(final CelebrityService celebrityService) {
        logger.entering(CelebrityController.class.getName(),
                "setCelebrityService("
                        + String.valueOf(celebrityService)
                        + ")");
        this.celebrityService = celebrityService;
    }

    @RequestMapping(value = "/celebrity", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Celebrity addCelebrity(@RequestBody Celebrity celebrity) {
        logger.entering(CelebrityController.class.getName(),
                "addCelebrity(" + celebrity + ")");
        return celebrityService.addCelebrity(celebrity);
    }

    @RequestMapping(value = "/celebrity/{IMDb_ID}",
            method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Celebrity getCelebrityById(@PathVariable("IMDb_ID") String imdbId) {
        logger.entering(CelebrityController.class.getName(),
                "getCelebrityById(" + imdbId + ")");
        return celebrityService.getCelebrityById(imdbId);
    }

    @RequestMapping(value = "/celebrity/{IMDb_ID}",
            method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteCelebrity(
            @PathVariable("IMDb_ID") String imdbId) {
        logger.entering(CelebrityController.class.getName(),
                "deleteCelebrity(" + imdbId + ")");
        celebrityService.deleteCelebrity(imdbId);
    }

    @RequestMapping(value = "/celebrity",
        method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Celebrity> queryCelebrities(
            @RequestParam(value = "start", required = false) Long start,
            @RequestParam(value = "end", required = false) Long end,
        @RequestParam(value = "latitude", required = false) Double latitude,
        @RequestParam(value = "longitude", required = false) Double longitude
    ){
        logger.entering(CelebrityController.class.getName(),
                "queryCelebrities("
            + start + ", " + end + ", " + latitude + ", " + longitude);

        return celebrityService.queryCelebrities(
                start, end, latitude, longitude);
    }

    /*
    @RequestMapping(value = "/celebrity/name/{name}",
            method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Celebrity> celebritiesByName(@PathVariable("name") String name)
    {
        logger.entering(CelebrityController.class.getName(),
                "celebritiesByName(" + name + ")");
        return celebrityService.getCelebritiesByName(name);
    }
    */

    @RequestMapping(value = "/sighting/{IMDb_ID}",
            method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public Celebrity addSighting(@PathVariable("IMDb_ID") String imdbId,
                                 @RequestBody Sighting sighting) {
        logger.entering(CelebrityController.class.getName(),
                "addSighting(" + sighting + ")");
        return celebrityService.addSighting(imdbId, sighting);
    }

    @RequestMapping(value = "/sighting",
            method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<CelebritySighting> celebritySightingsByLocation(
            @RequestParam(value = "start", required = false) Long start,
            @RequestParam(value = "end", required = false) Long end,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude",
                    required = false) Double longitude
    ){
        logger.entering(CelebrityController.class.getName(),
            "celebritySightingsByLocation("
                 + start + ", " + end + ", " + latitude + ", " + longitude);

        return celebrityService.queryCelebritySightings(
            start, end, latitude, longitude);
    }
}