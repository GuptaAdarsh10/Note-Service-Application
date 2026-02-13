package in.co.interview.noteservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DeviceNoteResponse {

    private Long id;
    private String note;
    private String createdBy;
    private LocalDateTime createdAt;
}
