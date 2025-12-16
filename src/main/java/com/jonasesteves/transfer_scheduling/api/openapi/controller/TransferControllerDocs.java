package com.jonasesteves.transfer_scheduling.api.openapi.controller;

import com.jonasesteves.transfer_scheduling.api.dto.TransferInput;
import com.jonasesteves.transfer_scheduling.api.dto.TransferOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;

import java.time.OffsetDateTime;
import java.util.UUID;

@Tag(name = "Transfers", description = "API for managing transfers")
public interface TransferControllerDocs {

    @Operation(
            summary = "Create a transfer",
            description = "Schedules a new transfer based on the provided input data."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Transfer scheduled successfully",
            content = @Content(schema = @Schema(implementation = TransferOutput.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(
                    schema = @Schema(
                            implementation = ProblemDetail.class,
                            example = """
                                {
                                    "type": "/errors/invalid-amount-value",
                                    "title": "Invalid amount value",
                                    "status": 400,
                                    "detail": "amount must be at least 1.00",
                                    "instance": "/api/transfers"
                                }
                            """
                    )
            )
    )
    TransferOutput create(@RequestBody(description = "Representation of a new Transfer") TransferInput transferInput);

    @Operation(
            summary = "Get transfer by ID",
            description = "Retrieves the details of a specific transfer using its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Transfer not found",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class,
                                    example = """
                                    {
                                        "type": "/errors/entity-not-found",
                                        "title": "Entity not found",
                                        "status": 404,
                                        "detail": "There is no transfer with code 019b21e4-d259-7bed-84e0-c9dd7a013523",
                                        "instance": "/api/transfers/019b21e4-d259-7bed-84e0-c9dd7a013523"
                                    }
                            """)))
            }
    )
    TransferOutput findById(
            @Parameter(description = "Transfer ID", example = "019b26d3-0ec3-7fac-baef-df0ad83bb9f8", required = true)
            UUID id);

    @Operation(
            summary = "Find transfers by date range",
            description = "Retrieves a paginated list of transfers scheduled within the specified date range.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data")
            }
    )
    Page<TransferOutput> findByDate(
            @Parameter(description = "Start date for the search") OffsetDateTime startDate,
            @Parameter(description = "End date for the search") OffsetDateTime endDate,
            Pageable pageable);

    @Operation(
            summary = "Update a transfer",
            description = "Updates the details of an existing transfer identified by its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ProblemDetail.class,
                                            example = """
                                            {
                                                "type": "/errors/invalid-amount-value",
                                                "title": "Invalid amount value",
                                                "status": 400,
                                                "detail": "amount must be at least 1.00",
                                                "instance": "/api/transfers"
                                            }
                                        """
                                    )
                            )),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Transfer not found",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class,
                                    example = """
                                    {
                                        "type": "/errors/entity-not-found",
                                        "title": "Entity not found",
                                        "status": 404,
                                        "detail": "There is no transfer with code 019b21e4-d259-7bed-84e0-c9dd7a013523",
                                        "instance": "/api/transfers/019b21e4-d259-7bed-84e0-c9dd7a013523"
                                    }
                            """)))
            }
    )
    TransferOutput update(
            @Parameter(description = "Transfer ID", example = "019b26d3-0ec3-7fac-baef-df0ad83bb9f8", required = true) UUID id,
            @RequestBody(description = "Representation of a Transfer to update") TransferInput transferInput);

    @Operation(
            summary = "Delete a transfer",
            description = "Deletes an existing transfer identified by its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Transfer deleted successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Transfer not found")
            }
    )
    void delete(@Parameter(description = "Transfer ID", example = "019b26d3-0ec3-7fac-baef-df0ad83bb9f8", required = true) UUID id);
}
