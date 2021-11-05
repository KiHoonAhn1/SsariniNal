package com.playssafy.playssafy.repository;

//import com.playssafy.playssafy.dto.member.Member;
import com.playssafy.playssafy.dto.play.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
//    Optional<Room> findRoomBym(String userId);

}
