package com.bloggios.user.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.payload.response.ExceptionResponse;
import com.bloggios.user.payload.response.ProfileInternalResponse;
import com.bloggios.user.payload.response.ProfileResponse;
import com.bloggios.user.service.FollowService;
import com.bloggios.user.service.ProfileService;
import com.bloggios.user.utils.AsyncUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.controller
 * Created_on - June 07 - 2024
 * Created_at - 17:44
 */

@RestController
@RequestMapping(EndpointConstants.OpenController.BASE_PATH)
@RequiredArgsConstructor
public class OpenController {

    private final ProfileService profileService;
    private final FollowService followService;

    @GetMapping(EndpointConstants.OpenController.PROFILE_INTERNAL_RESPONSE)
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ProfileInternalResponse.class)
                    )),
                    @ApiResponse(description = "No Content", responseCode = "401", content = {
                            @Content(schema = @Schema(implementation = Void.class))
                    }),
                    @ApiResponse(description = "FORBIDDEN", responseCode = "403", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(description = "BAD REQUEST", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    public ResponseEntity<ProfileInternalResponse> getProfileInternalResponse(@PathVariable String userId) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.getProfileInternalResponse(userId)));
    }

    @GetMapping(EndpointConstants.OpenController.Profile.USER_PROFILE)
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class)
                    )),
                    @ApiResponse(description = "No Content", responseCode = "401", content = {
                            @Content(schema = @Schema(implementation = Void.class))
                    }),
                    @ApiResponse(description = "FORBIDDEN", responseCode = "403", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(description = "BAD REQUEST", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    public ResponseEntity<ProfileResponse> getUserProfile(@RequestParam String username, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.getUserProfile(username, authenticatedUser)));
    }

    @GetMapping(EndpointConstants.OpenController.Profile.FETCH_PROFILES_USERNAME)
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ListResponse.class)
                    )),
                    @ApiResponse(description = "No Content", responseCode = "401", content = {
                            @Content(schema = @Schema(implementation = Void.class))
                    }),
                    @ApiResponse(description = "FORBIDDEN", responseCode = "403", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(description = "BAD REQUEST", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    public ResponseEntity<ListResponse> fetchProfilesUsingUsername(@RequestParam String username) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.fetchProfilesUsingUsername(username)));
    }

}
