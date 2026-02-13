package in.co.interview.noteservice.controller;

import in.co.interview.noteservice.dto.request.CreateDeviceNoteRequest;
import in.co.interview.noteservice.dto.response.DeviceNoteResponse;
import in.co.interview.noteservice.service.DeviceNoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices/{deviceId}/notes")
public class DeviceNoteController {

    private static final Logger log = LoggerFactory.getLogger(DeviceNoteController.class);
    private final DeviceNoteService service;

    public DeviceNoteController(DeviceNoteService service) {
        this.service = service;
    }

    @PostMapping
    public DeviceNoteResponse createNote(
            @PathVariable Long deviceId,
            @RequestHeader(value = "X-User", required = false) String user,
            @RequestBody CreateDeviceNoteRequest request
    ) {
        log.info("Create note request: deviceId={}, user={}", deviceId, user);
        return service.createNote(deviceId, user, request);
    }

    @GetMapping
    public List<DeviceNoteResponse> listNotes(
            @PathVariable Long deviceId,
            @RequestParam(defaultValue = "20") Integer limit
    ) {
        return service.listNotes(deviceId, limit);
    }
}
