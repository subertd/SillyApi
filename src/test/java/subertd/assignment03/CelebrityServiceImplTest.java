package subertd.assignment03;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CelebrityServiceImplTest {

    private static final Double validLatitude = 5.8;
    private static final Double validLongitude = 87.0;
    private static final Long validStart = new Long(35283);
    private static final Long validEnd = new Long(666667);
    private static List<Celebrity> celebrities;

    private static final Long datetime1 = 0L;
    private static final Long datetime2 = 5L;
    private static final Long datetime3 = 10L;

    private static Sighting sighting1;
    private static Sighting sighting2;
    private static Sighting sighting3;

    private static Celebrity celebrity1;
    private static Celebrity celebrity2;
    private static Celebrity celebrity3;

    private static List<CelebritySighting> fullList;
    private static List<CelebritySighting> filteredList1;
    private static List<CelebritySighting> filteredList2;

    @Mock
    private CelebrityRepository repository;

    private CelebrityServiceImpl service;

    @BeforeClass
    public static void onlyOnce() {
        sighting1 = new Sighting();
        sighting1.setDatetime(new Date(datetime1));
        sighting1.setLatitude(validLatitude);
        sighting1.setLongitude(validLongitude);

        sighting2 = new Sighting();
        sighting2.setDatetime(new Date(datetime2));
        sighting2.setLatitude(validLatitude);
        sighting2.setLongitude(validLongitude);

        sighting3 = new Sighting();
        sighting3.setDatetime(new Date(datetime3));
        sighting3.setLatitude(validLatitude);
        sighting3.setLongitude(validLongitude);

        celebrity1 = new Celebrity();
        celebrity1.setImdbId("testId1");
        celebrity1.setName("testName");
        celebrity1.setSightings(Arrays.asList(sighting1));

        celebrity2 = new Celebrity();
        celebrity2.setImdbId("testId2");
        celebrity2.setName("testName");
        celebrity2.setSightings(Arrays.asList(sighting2));

        celebrity3 = new Celebrity();
        celebrity3.setImdbId("testId3");
        celebrity3.setName("testName");
        celebrity3.setSightings(Arrays.asList(sighting3));

        fullList = new ArrayList<CelebritySighting>();
        fullList.add(CelebritySightingFactory.getInstance("testId1", "testName", sighting1));
        fullList.add(CelebritySightingFactory.getInstance("testId2", "testName", sighting2));
        fullList.add(CelebritySightingFactory.getInstance("testId3", "testName", sighting3));

        filteredList1 = new ArrayList<CelebritySighting>();
        filteredList1.add(fullList.get(1));

        filteredList2 = new ArrayList<CelebritySighting>();
        filteredList2.add(fullList.get(1));
        filteredList2.add(fullList.get(2));

        celebrities = Arrays.asList(celebrity1, celebrity2, celebrity3);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CelebrityServiceImpl();
        service.setCelebrityRepository(repository);
    }

    @Test
    public void testQueryCelebrities_noParameters_shouldCallRepository() {
        service.queryCelebrities(null, null, null, null);
        Mockito.verify(repository).findCelebrities();
    }

    @Test
    public void testQueryCelebrities_validParameters_shouldCallRepository() {
        service.queryCelebrities(validStart, validEnd, validLatitude, validLongitude);
        Mockito.verify(repository).findCelebritiesByLocation(validLatitude, validLongitude);
    }



    @Test
    public void testQueryCelebrities_noParameters_shouldReturnFullList() {
        Mockito.when(repository.findCelebrities()).thenReturn(celebrities);
        final List<Celebrity> expected = celebrities;
        final List<Celebrity> actual = service.queryCelebrities(null, null, null, null);
        assertEquals(expected, actual);
    }

    @Test
    public void testQueryCelebrities_validStart_shouldFilterByDateToCurrent() {
        Mockito.when(repository.findCelebrities()).thenReturn(celebrities);
        final List<Celebrity> expected = Arrays.asList(celebrity2, celebrity3);
        final List<Celebrity> actual = service.queryCelebrities(datetime1 + 1, null, null, null);
        assertEquals(expected, actual);
    }

    @Test
    public void testQueryCelebrities_validStartAndEnd_shouldFilterByDate() {
        Mockito.when(repository.findCelebrities()).thenReturn(celebrities);
        final List<Celebrity> expected = Arrays.asList(celebrity2);
        final List<Celebrity> actual = service.queryCelebrities(datetime1 + 1, datetime3 - 1, null, null);
        assertEquals(expected, actual);
    }

    @Test
    public void testQueryCelebrities_validStartEndLatitudeAndLongitude_shouldFilterByDate() {
        Mockito.when(repository.findCelebritiesByLocation(validLatitude, validLongitude)).thenReturn(celebrities);
        final List<Celebrity> expected = Arrays.asList(celebrity2);
        final List<Celebrity> actual = service.queryCelebrities(
                datetime1 + 1, datetime3 - 1, validLatitude, validLongitude);
        assertEquals(expected, actual);
    }



    @Test
    public void testQueryCelebritySightings_noParameters_shouldReturnFullList() {
        Mockito.when(repository.findCelebrities()).thenReturn(celebrities);
        final List<CelebritySighting> expected = fullList;
        final List<CelebritySighting> actual = service.queryCelebritySightings(null, null, null, null);
        assertCelebritySightingListEquals(expected, actual);
    }

    @Test
    public void testQueryCelebritySightings_validStartAndEnd_shouldFilterByDate() {
        Mockito.when(repository.findCelebrities()).thenReturn(celebrities);
        final List<CelebritySighting> expected = filteredList1;
        final List<CelebritySighting> actual = service.queryCelebritySightings(datetime1 + 1, datetime3 - 1, null, null);
        assertCelebritySightingListEquals(expected, actual);
    }

    @Test
    public void testQueryCelebritySightings_validStart_shouldFilterByDateToCurrent() {
        Mockito.when(repository.findCelebrities()).thenReturn(celebrities);
        final List<CelebritySighting> expected = filteredList2;
        final List<CelebritySighting> actual = service.queryCelebritySightings(datetime1 + 1, null, null, null);
        assertCelebritySightingListEquals(expected, actual);
    }

    @Test
    public void testQueryCelebritySightings_validStartEndLatitudeAndLongitude_shouldFilterByDate() {
        Mockito.when(repository.findCelebritiesByLocation(validLatitude, validLongitude)).thenReturn(celebrities);
        final List<CelebritySighting> expected = filteredList1;
        final List<CelebritySighting> actual = service.queryCelebritySightings(
                datetime1 + 1, datetime3 - 1, validLatitude, validLongitude);
        assertCelebritySightingListEquals(expected, actual);
    }

    private void assertCelebritySightingListEquals(final List<CelebritySighting> expected,
                                                   final List<CelebritySighting> actual)
    {
        assertEquals("Different number of elements", expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            CelebritySighting expectedSighting = expected.get(i);
            CelebritySighting actualSighting = actual.get(i);

            assertEquals(expectedSighting.getImdbId(), actualSighting.getImdbId());
            assertEquals(expectedSighting.getName(), actualSighting.getName());
            assertEquals(expectedSighting.getLatitude(), actualSighting.getLatitude());
            assertEquals(expectedSighting.getLongitude(), actualSighting.getLongitude());
            assertEquals(expectedSighting.getDatetime(), actualSighting.getDatetime());
        }
    }
}
