package org.springframework.data.xap.examples.advanced.transaction;

import net.jini.core.transaction.Transaction;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Anna_Babich.
 */
@Component
public class TransactionalService {
    private static Logger log = LoggerFactory.getLogger(TransactionExample.class);

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRooms(List<MeetingRoom> rooms) {
        GigaSpace space = meetingRoomRepository.space();
        Transaction transaction = space.getCurrentTransaction();
        log.info("Current transaction: " + transaction);
       
       for (MeetingRoom room: rooms){
           log.info("Try to save " + room.getName());
           if (meetingRoomRepository.existsById(room.getName())){
               throw new RoomNameIsUnavailableException("Unavailable name");
           }
           meetingRoomRepository.save(room);
           log.info(room.getName() + " room has been saved.");
       }
    }
}
