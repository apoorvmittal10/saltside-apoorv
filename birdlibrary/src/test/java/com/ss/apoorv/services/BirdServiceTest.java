package com.ss.apoorv.services;

import com.ss.apoorv.bo.transport.BirdRequest;
import com.ss.apoorv.bo.transport.BirdResponse;
import com.ss.apoorv.exceptions.WebApplicationException;
import com.ss.apoorv.mongo.model.BirdModel;
import com.ss.apoorv.mongo.repositories.BirdRepository;
import com.ss.apoorv.util.SSErrorEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.impl.HttpHeadersImpl;
import org.apache.cxf.message.MessageImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by apoorv on 03/05/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ss-parent-config-test.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BirdServiceTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
	
	@Autowired
	private BirdRepository birdRepository;
	
	@Autowired
	private BirdService birdService;

	// Test cases for all birds fetch method

	@Test
	public void testBirdServiceGetAllBirdsWithHeaders(){
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(5, 5));
		List<BirdResponse> birdResponse = birdService.getBirds(new HttpHeadersImpl(new MessageImpl()));

		assertSuccessfulResponse(birdResponse, birdModelMap);
	}

	@Test
	public void testBirdServiceGetAllBirdsWithCustomDate(){
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(5, 5));
		// Add extra entry with custom date
		birdModelMap.putAll(saveBirdsDataInDB(getBirdData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), getRandomContinents(2), getRandomDate(), null)));

		List<BirdResponse> birdResponse = birdService.getBirds(new HttpHeadersImpl(new MessageImpl()));

		assertSuccessfulResponse(birdResponse, birdModelMap);
	}

	@Test
	public void testBirdServiceGetAllBirdsWithCustomVisibility(){
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(5, 5));
		// Add extra entry with custom date and visibility
		birdModelMap.putAll(saveBirdsDataInDB(getBirdData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), getRandomContinents(2), getRandomDate(), true)));

		List<BirdResponse> birdResponse = birdService.getBirds(new HttpHeadersImpl(new MessageImpl()));

		assertSuccessfulResponse(birdResponse, birdModelMap);
	}
	
	@Test
	public void testBirdServiceGetAllBirdsWithNullHeaders(){
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(5, 5));
		List<BirdResponse> birdResponse = birdService.getBirds(null);

		assertSuccessfulResponse(birdResponse, birdModelMap);
	}

	@Test
	public void testBirdServiceGetAllBirdsWithNoData(){
		boolean isException = false;
		try {
			birdService.getBirds(new HttpHeadersImpl(new MessageImpl()));
		} catch (Exception e) {
			isException = true;
			assertTrue("WebApplicationException expected", e instanceof WebApplicationException);
			assertEquals("Incorrect http status", SSErrorEnum.BIRD_NOT_FOUND.getHttpStatus(), ((WebApplicationException) e).getHttpStatus());
		}

		assertTrue("Exception expected", isException);
	}

	// Test cases for fetching bird with Id

	@Test
	public void testBirdServiceGetBirdWithHeaders() {
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(5, 5));

		Iterator<Map.Entry<String, BirdModel>> it = birdModelMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, BirdModel> pair = it.next();
			BirdResponse birdResponse = birdService.getBird(new HttpHeadersImpl(new MessageImpl()), pair.getKey());

			assertSuccessfulResponse(birdResponse, pair.getValue());
		}
	}

	@Test
	public void testBirdServiceGetBirdWithNullHeaders() {
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(5, 5));

		Iterator<Map.Entry<String, BirdModel>> it = birdModelMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, BirdModel> pair = it.next();
			BirdResponse birdResponse = birdService.getBird(null, pair.getKey());

			assertSuccessfulResponse(birdResponse, pair.getValue());
		}
	}

	@Test
	public void testBirdServiceGetBirdWithCustomDate() {
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(5, 5));
		// Add extra entry with custom date
		birdModelMap.putAll(saveBirdsDataInDB(getBirdData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), getRandomContinents(2), getRandomDate(), null)));

		Iterator<Map.Entry<String, BirdModel>> it = birdModelMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, BirdModel> pair = it.next();
			BirdResponse birdResponse = birdService.getBird(new HttpHeadersImpl(new MessageImpl()), pair.getKey());

			assertSuccessfulResponse(birdResponse, pair.getValue());
		}
	}

	@Test
	public void testBirdServiceGetBirdWithCustomVisibility() {
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(5, 5));
		// Add extra entry with custom date and visibility
		birdModelMap.putAll(saveBirdsDataInDB(getBirdData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), getRandomContinents(2), getRandomDate(), true)));

		Iterator<Map.Entry<String, BirdModel>> it = birdModelMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, BirdModel> pair = it.next();
			BirdResponse birdResponse = birdService.getBird(new HttpHeadersImpl(new MessageImpl()), pair.getKey());

			assertSuccessfulResponse(birdResponse, pair.getValue());
		}
	}

	@Test
	public void testBirdServiceGetBirdWithIncorrectBirdId(){
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(5, 5));

		boolean isException = false;
		try {
			birdService.getBird(new HttpHeadersImpl(new MessageImpl()), RandomStringUtils.randomAlphabetic(10));
		} catch (Exception e) {
			isException = true;
			assertTrue("WebApplicationException expected", e instanceof WebApplicationException);
			assertEquals("Incorrect http status", SSErrorEnum.BIRD_NOT_FOUND.getHttpStatus(), ((WebApplicationException) e).getHttpStatus());
		}

		assertTrue("Exception expected", isException);
	}

	@Test
	public void testBirdServiceGetBirdWithNoDataInDB(){
		boolean isException = false;
		try {
			birdService.getBird(new HttpHeadersImpl(new MessageImpl()), RandomStringUtils.randomAlphabetic(10));
		} catch (Exception e) {
			isException = true;
			assertTrue("WebApplicationException expected", e instanceof WebApplicationException);
			assertEquals("Incorrect http status", SSErrorEnum.BIRD_NOT_FOUND.getHttpStatus(), ((WebApplicationException) e).getHttpStatus());
		}

		assertTrue("Exception expected", isException);
	}

	// Test cases for saving bird data

	@Test
	public void testBirdServiceSaveBirdWithHeaders(){
		BirdRequest birdRequest = getBirdRequestData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), getRandomContinents(5), getRandomDate(), true);

		Response response = birdService.saveBird(new HttpHeadersImpl(new MessageImpl()), birdRequest);

		assertNotNull("Response is null", response);
		assertEquals("Incorrect HTTP status", Response.Status.CREATED.getStatusCode(), response.getStatus());

		BirdModel birdModel = birdRepository.findAll().get(0);
		assertDataInDB(birdRequest, birdModel);
		assertSuccessfulResponse((BirdResponse) response.getEntity(), birdModel);
	}

	@Test
	public void testBirdServiceSaveBirdWithNullHeaders(){
		BirdRequest birdRequest = getBirdRequestData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), getRandomContinents(5), getRandomDate(), true);

		Response response = birdService.saveBird(null, birdRequest);

		assertNotNull("Response is null", response);
		assertEquals("Incorrect HTTP status", Response.Status.CREATED.getStatusCode(), response.getStatus());

		BirdModel birdModel = birdRepository.findAll().get(0);
		assertDataInDB(birdRequest, birdModel);
		assertSuccessfulResponse((BirdResponse) response.getEntity(), birdModel);
	}

	@Test
	public void testBirdServiceSaveBirdWithDefaultDate(){
		BirdRequest birdRequest = getBirdRequestData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), getRandomContinents(5), null, true);

		Response response = birdService.saveBird(null, birdRequest);

		assertNotNull("Response is null", response);
		assertEquals("Incorrect HTTP status", Response.Status.CREATED.getStatusCode(), response.getStatus());

		BirdModel birdModel = birdRepository.findAll().get(0);
		assertDataInDB(birdRequest, birdModel);
		assertSuccessfulResponse((BirdResponse) response.getEntity(), birdModel);
	}

	@Test
	public void testBirdServiceSaveBirdWithDefaultVisibility(){
		BirdRequest birdRequest = getBirdRequestData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), getRandomContinents(5), getRandomDate(), null);

		Response response = birdService.saveBird(null, birdRequest);

		assertNotNull("Response is null", response);
		assertEquals("Incorrect HTTP status", Response.Status.CREATED.getStatusCode(), response.getStatus());

		BirdModel birdModel = birdRepository.findAll().get(0);
		assertDataInDB(birdRequest, birdModel);
		assertSuccessfulResponse((BirdResponse) response.getEntity(), birdModel);
	}

	@Test
	public void testBirdServiceSaveBirdWithMissingName(){
		BirdRequest birdRequest = getBirdRequestData(null,
				RandomStringUtils.randomAlphabetic(5), getRandomContinents(5), getRandomDate(), null);

		boolean isException = false;
		try {
			birdService.saveBird(new HttpHeadersImpl(new MessageImpl()), birdRequest);
		} catch (Exception e) {
			isException = true;
			assertTrue("WebApplicationException expected", e instanceof WebApplicationException);
			assertEquals("Incorrect http status", SSErrorEnum.BAD_REQUEST.getHttpStatus(), ((WebApplicationException) e).getHttpStatus());
		}

		assertTrue("Exception expected", isException);
	}

	@Test
	public void testBirdServiceSaveBirdWithMissingFamily(){
		BirdRequest birdRequest = getBirdRequestData(RandomStringUtils.randomAlphabetic(5),
				null, getRandomContinents(5), getRandomDate(), null);

		boolean isException = false;
		try {
			birdService.saveBird(new HttpHeadersImpl(new MessageImpl()), birdRequest);
		} catch (Exception e) {
			isException = true;
			assertTrue("WebApplicationException expected", e instanceof WebApplicationException);
			assertEquals("Incorrect http status", SSErrorEnum.BAD_REQUEST.getHttpStatus(), ((WebApplicationException) e).getHttpStatus());
		}

		assertTrue("Exception expected", isException);
	}

	@Test
	public void testBirdServiceSaveBirdWithMissingContinents(){
		BirdRequest birdRequest = getBirdRequestData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), null, getRandomDate(), null);

		boolean isException = false;
		try {
			birdService.saveBird(new HttpHeadersImpl(new MessageImpl()), birdRequest);
		} catch (Exception e) {
			isException = true;
			assertTrue("WebApplicationException expected", e instanceof WebApplicationException);
			assertEquals("Incorrect http status", SSErrorEnum.BAD_REQUEST.getHttpStatus(), ((WebApplicationException) e).getHttpStatus());
		}

		assertTrue("Exception expected", isException);
	}

	@Test
	public void testBirdServiceSaveBirdWithBlankContinents(){
		BirdRequest birdRequest = getBirdRequestData(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5), new LinkedHashSet<>(), getRandomDate(), null);

		boolean isException = false;
		try {
			birdService.saveBird(new HttpHeadersImpl(new MessageImpl()), birdRequest);
		} catch (Exception e) {
			isException = true;
			assertTrue("WebApplicationException expected", e instanceof WebApplicationException);
			assertEquals("Incorrect http status", SSErrorEnum.BAD_REQUEST.getHttpStatus(), ((WebApplicationException) e).getHttpStatus());
		}

		assertTrue("Exception expected", isException);
	}

	// Test cases for delete bird data

	@Test
	public void testBirdServiceDeleteBirdWithHeaders() {
		int count = 5;
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(count, 5));

		Iterator<Map.Entry<String, BirdModel>> it = birdModelMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, BirdModel> pair = it.next();
			Response response = birdService.deleteBird(new HttpHeadersImpl(new MessageImpl()), pair.getKey());

			assertNotNull("Response is null", response);
			assertEquals("Incorrect HTTP status", Response.Status.OK.getStatusCode(), response.getStatus());

			assertEquals("Data not deleted from DB", --count, birdRepository.count());
		}
	}

	@Test
	public void testBirdServiceDeleteBirdWithNullHeaders() {
		int count = 2;
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(count, 5));

		Iterator<Map.Entry<String, BirdModel>> it = birdModelMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, BirdModel> pair = it.next();
			Response response = birdService.deleteBird(null, pair.getKey());

			assertNotNull("Response is null", response);
			assertEquals("Incorrect HTTP status", Response.Status.OK.getStatusCode(), response.getStatus());

			assertEquals("Data not deleted from DB", --count, birdRepository.count());
		}
	}

	@Test
	public void testBirdServiceDeleteBirdWithNullId() {
		int count = 2;
		Map<String, BirdModel> birdModelMap = saveBirdsDataInDB(getRandomDefaultBirdData(count, 5));

		boolean isException = false;
		try {
			birdService.deleteBird(null, null);
		} catch (Exception e) {
			isException = true;
			assertTrue("WebApplicationException expected", e instanceof WebApplicationException);
			assertEquals("Incorrect http status", SSErrorEnum.BIRD_NOT_FOUND.getHttpStatus(), ((WebApplicationException) e).getHttpStatus());
		}

		assertTrue("Exception expected", isException);
	}

	@Test
	public void testBirdServiceDeleteBirdWithMissingDataInDB() {
		boolean isException = false;
		try {
			birdService.deleteBird(null, RandomStringUtils.randomAlphabetic(5));
		} catch (Exception e) {
			isException = true;
			assertTrue("WebApplicationException expected", e instanceof WebApplicationException);
			assertEquals("Incorrect http status", SSErrorEnum.BIRD_NOT_FOUND.getHttpStatus(), ((WebApplicationException) e).getHttpStatus());
		}

		assertTrue("Exception expected", isException);
	}

	private void assertSuccessfulResponse(List<BirdResponse> birdResponseList, Map<String, BirdModel> birdModelMap) {
		assertNotNull("Response is null", birdResponseList);
		assertEquals("Data is incorrect in response", birdModelMap.size(), birdResponseList.size());

		for (BirdResponse birdResponse: birdResponseList) {
			assertNotNull("Bird response is null", birdResponse);
			assertTrue("Incorrect bird id data in response", birdModelMap.containsKey(birdResponse.getId()));
			BirdModel birdModel = birdModelMap.get(birdResponse.getId());

			assertEquals("Incorrect bird name in response",birdModel.getName(), birdResponse.getName());
			assertEquals("Incorrect bird family in response", birdModel.getFamily(), birdResponse.getFamily());
			assertEquals("Incorrect bird visibility in response", birdModel.isVisible(), birdResponse.getVisible());
			assertEquals("Incorrect date in response", sdf.format(birdModel.getAdded()), birdResponse.getAdded());
			assertEquals("Incorrect continents in response", birdModel.getContinents().size(), birdResponse.getContinents().size());

			for (String continent: birdModel.getContinents()) {
				assertTrue("Missing continent data in response", birdResponse.getContinents().contains(continent));
			}
		}
	}

	private void assertSuccessfulResponse(BirdResponse birdResponse, BirdModel birdModel) {
		assertNotNull("Bird response is null", birdResponse);
		assertEquals("Incorrect bird id data in response", birdModel.getId(), birdResponse.getId());
		assertEquals("Incorrect bird name in response",birdModel.getName(), birdResponse.getName());
		assertEquals("Incorrect bird family in response", birdModel.getFamily(), birdResponse.getFamily());
		assertEquals("Incorrect bird visibility in response", birdModel.isVisible(), birdResponse.getVisible());
		assertEquals("Incorrect date in response", sdf.format(birdModel.getAdded()), birdResponse.getAdded());
		assertEquals("Incorrect continents in response", birdModel.getContinents().size(), birdResponse.getContinents().size());

		for (String continent: birdModel.getContinents()) {
			assertTrue("Missing continent data in response", birdResponse.getContinents().contains(continent));
		}
	}

	private void assertDataInDB(BirdRequest birdRequest, BirdModel birdModel) {
		assertNotNull("Data in DB doesn't exist", birdModel);

		assertEquals("Incorrect bird name in DB", birdRequest.getName(), birdModel.getName());
		assertEquals("Incorrect bird family in DB", birdRequest.getFamily(), birdModel.getFamily());
		assertEquals("Incorrect bird visibility in DB", birdRequest.getVisible() != null? birdRequest.getVisible(): false,
				birdModel.isVisible());
		try {
			if (birdRequest.getAdded() != null) {
				assertEquals("Incorrect date in DB", sdf.parse(birdRequest.getAdded()), birdModel.getAdded());
			} else {
				assertTrue("Incorrect date in DB", (new Date().getTime() - birdModel.getAdded().getTime()) < 100);
			}
		} catch (ParseException e) {
			assertTrue("Exception occurred", false);
		}
		assertEquals("Incorrect continents in DB", birdRequest.getContinents().size(), birdModel.getContinents().size());

		for (String continent: birdRequest.getContinents()) {
			assertTrue("Missing continent data in DB", birdModel.getContinents().contains(continent));
		}
	}

	private List<BirdModel> getRandomDefaultBirdData(int count, int continentsCount) {
		List<BirdModel> birdModels = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			birdModels.add(getBirdData(RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(8),
					getRandomContinents(continentsCount), null, null));
		}
		return birdModels;
	}

	private BirdModel getBirdData(String name, String family, Set<String> continents,
								   Date date, Boolean visibility) {
		BirdModel birdModel = new BirdModel();

		if (name != null) {
			birdModel.setName(name);
		}

		if (family != null) {
			birdModel.setFamily(family);
		}

		if (continents != null) {
			birdModel.setContinents(continents);
		}

		if (date != null) {
			birdModel.setAdded(date);
		}

		if (visibility != null) {
			birdModel.setVisible(visibility);
		}
		return birdModel;
	}

	private Map<String, BirdModel> saveBirdsDataInDB(List<BirdModel> birdModels){
		birdRepository.save(birdModels);

		Map<String, BirdModel> birdModelMap = new HashMap<>();
		for (BirdModel birdModel: birdModels) {
			birdModelMap.put(birdModel.getId(), birdModel);
		}
		return birdModelMap;
	}

	private Map<String, BirdModel> saveBirdsDataInDB(BirdModel birdModel){
		birdRepository.save(birdModel);

		Map<String, BirdModel> birdModelMap = new HashMap<>();
		birdModelMap.put(birdModel.getId(), birdModel);

		return birdModelMap;
	}

	private BirdRequest getBirdRequestData(String name, String family, Set<String> continents,
								  Date date, Boolean visibility) {
		BirdRequest birdRequest = new BirdRequest();

		if (name != null) {
			birdRequest.setName(name);
		}

		if (family != null) {
			birdRequest.setFamily(family);
		}

		if (continents != null) {
			birdRequest.setContinents(continents);
		}

		if (date != null) {
			birdRequest.setAdded(sdf.format(date));
		}

		if (visibility != null) {
			birdRequest.setVisible(visibility);
		}
		return birdRequest;
	}

	private Date getRandomDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, Integer.parseInt(RandomStringUtils.randomNumeric(1)));
		return c.getTime();
	}

	private Set<String> getRandomContinents(int count) {
		Set<String> continents = new LinkedHashSet<>();
		for (int j = 0;j < count; j++) {
			continents.add(RandomStringUtils.randomAlphabetic(10));
		}
		return continents;
	}

	// Remove bird data from database
	private void removeBirdsDataFromDB(){
		birdRepository.deleteAll();
	}

	@After
	public void tearDown(){
		removeBirdsDataFromDB();
	}
}
