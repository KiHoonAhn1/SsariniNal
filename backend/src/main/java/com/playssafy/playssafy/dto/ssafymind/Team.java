package com.playssafy.playssafy.dto.ssafymind;

import com.playssafy.playssafy.dto.waitroom.Participant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Team {
    @Id
    private int teamNo;
    private List<Participant> members = new ArrayList<>();
}
