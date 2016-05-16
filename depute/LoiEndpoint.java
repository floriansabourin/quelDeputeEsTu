package depute;

import depute.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "loiendpoint", namespace = @ApiNamespace(ownerDomain = "mycompany.com", ownerName = "mycompany.com", packagePath = "services"))
public class LoiEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listLoi")
	public CollectionResponse<Loi> listLoi(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Loi> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Loi.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Loi>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Loi obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Loi> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getLoi")
	public Loi getLoi(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Loi loi = null;
		try {
			loi = mgr.getObjectById(Loi.class, id);
		} finally {
			mgr.close();
		}
		return loi;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param loi the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertLoi")
	public Loi insertLoi(Loi loi) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsLoi(loi)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(loi);
		} finally {
			mgr.close();
		}
		return loi;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param loi the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateLoi")
	public Loi updateLoi(Loi loi) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsLoi(loi)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(loi);
		} finally {
			mgr.close();
		}
		return loi;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeLoi")
	public void removeLoi(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Loi loi = mgr.getObjectById(Loi.class, id);
			mgr.deletePersistent(loi);
		} finally {
			mgr.close();
		}
	}

	private boolean containsLoi(Loi loi) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Loi.class, loi.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
