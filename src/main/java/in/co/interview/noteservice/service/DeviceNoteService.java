package in.co.interview.noteservice.service;

import in.co.interview.noteservice.dto.request.CreateDeviceNoteRequest;
import in.co.interview.noteservice.dto.response.DeviceNoteResponse;
import in.co.interview.noteservice.entity.DeviceNote;
import in.co.interview.noteservice.exception.BadRequestException;
import in.co.interview.noteservice.repository.DeviceNoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceNoteService {

    private static final Logger log = LoggerFactory.getLogger(DeviceNoteService.class);
    private final DeviceNoteRepository repository;

    public DeviceNoteService(DeviceNoteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DeviceNoteResponse createNote(Long deviceId, String user, CreateDeviceNoteRequest request) {

        if (user == null || user.isBlank()) {
            log.warn("Missing X-User header");
            user = "SYSTEM";
        }

        if (request.note() == null || request.note().isBlank()) {
            log.warn("Blank note received");
            throw new BadRequestException("Note must not be blank");
        }

        if (request.note().length() > 1000) {
            throw new BadRequestException("Note must not exceed 1000 characters");
        }

        DeviceNote note = new DeviceNote();
        note.setDeviceId(deviceId);
        note.setNote(request.note());
        note.setCreatedBy(user);

        DeviceNote saved = repository.save(note);

        log.info("Note created successfully: noteId={}, deviceId={}", saved.getId(), deviceId);

        return new DeviceNoteResponse(
                saved.getId(),
                saved.getNote(),
                saved.getCreatedBy(),
                saved.getCreatedAt()
        );
    }

    public List<DeviceNoteResponse> listNotes(Long deviceId, Integer limit) {

        if (limit == null) limit = 20;
        if (limit < 1 || limit > 100) {
            throw new BadRequestException("Limit must be between 1 and 100");
        }

        return repository
                .findByDeviceIdOrderByCreatedAtDesc(deviceId, PageRequest.of(0, limit))
                .stream()
                .map(n -> new DeviceNoteResponse(
                        n.getId(),
                        n.getNote(),
                        n.getCreatedBy(),
                        n.getCreatedAt()
                ))
                .toList();
    }
}
