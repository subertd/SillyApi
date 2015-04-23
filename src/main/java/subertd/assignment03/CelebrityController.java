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

    @RequestMapping(value = "/celebrity/IMDb_ID/{IMDb_ID}",
            method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Celebrity celebrityByImdbId(@PathVariable("IMDb_ID") String imdbId) {
        logger.entering(CelebrityController.class.getName(),
                "celebrityByImdbId("
                    + String.valueOf(imdbId)
                    + ")");
        return celebrityService.getCelebrityByImdbId(imdbId);
    }

    @RequestMapping(value = "/celebrity/name/{name}", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public List<Celebrity> celebritiesByName(@PathVariable("name") String name) {
        logger.entering(CelebrityController.class.getName(),
                "celebritiesByName(" + name + ")");
        return celebrityService.getCelebritiesByName(name);
    }
}