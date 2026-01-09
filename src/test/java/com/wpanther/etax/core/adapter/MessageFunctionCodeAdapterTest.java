package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.MessageFunctionCodeAdapter;
import com.wpanther.etax.core.entity.MessageFunctionCode;
import com.wpanther.etax.core.repository.MessageFunctionCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MessageFunctionCode Adapter Tests")
public class MessageFunctionCodeAdapterTest {

    @Mock
    private MessageFunctionCodeRepository repository;

    @InjectMocks
    private MessageFunctionCodeAdapter adapter;

    private MessageFunctionCode cancellation;
    private MessageFunctionCode original;
    private MessageFunctionCode replacement;
    private MessageFunctionCode change;

    @BeforeEach
    public void setUp() {
        new MessageFunctionCodeAdapter();
        adapter.setRepository(repository);

        cancellation = new MessageFunctionCode("1", "Cancellation", "Cancellation message");
        cancellation.setCategory("Cancellation");

        original = new MessageFunctionCode("9", "Original", "Original transmission");
        original.setCategory("Original");

        replacement = new MessageFunctionCode("5", "Replacement", "Replacement message");
        replacement.setCategory("Modification");

        change = new MessageFunctionCode("4", "Change", "Change message");
        change.setCategory("Modification");
    }

    @Test
    @DisplayName("Should marshal entity to code")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(cancellation);
        assertEquals("1", result);
    }

    @Test
    @DisplayName("Should unmarshal valid code")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));

        MessageFunctionCode result = adapter.unmarshal("1");

        assertNotNull(result);
        assertEquals("1", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("99")).thenReturn(Optional.empty());

        MessageFunctionCode result = adapter.unmarshal("99");

        assertNotNull(result);
        assertEquals("99", result.getCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("1")).thenReturn(true);
        assertTrue(MessageFunctionCodeAdapter.isValid("1"));
    }

    @Test
    @DisplayName("getMessageFunctionName should return name")
    public void testGetMessageFunctionName() {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));
        assertEquals("Cancellation", MessageFunctionCodeAdapter.getMessageFunctionName("1"));
    }

    @Test
    @DisplayName("getMessageFunctionCategory should return category")
    public void testGetMessageFunctionCategory() {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));
        assertEquals("Cancellation", MessageFunctionCodeAdapter.getMessageFunctionCategory("1"));
    }

    @Test
    @DisplayName("isModification should return true for modification codes")
    public void testIsModification() {
        when(repository.findByCode("4")).thenReturn(Optional.of(change));
        assertTrue(MessageFunctionCodeAdapter.isModification("4"));
    }

    @Test
    @DisplayName("isOriginal should return true for original")
    public void testIsOriginal() {
        when(repository.findByCode("9")).thenReturn(Optional.of(original));
        assertTrue(MessageFunctionCodeAdapter.isOriginal("9"));
    }

    @Test
    @DisplayName("isOriginal should return false for non-original")
    public void testIsOriginalNonOriginal() {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));
        assertFalse(MessageFunctionCodeAdapter.isOriginal("1"));
    }

    @Test
    @DisplayName("isAcceptance should return false for non-acceptance")
    public void testIsAcceptance() {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));
        assertFalse(MessageFunctionCodeAdapter.isAcceptance("1"));
    }

    @Test
    @DisplayName("isCancellation should return true for cancellation")
    public void testIsCancellation() {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));
        assertTrue(MessageFunctionCodeAdapter.isCancellation("1"));
    }

    @Test
    @DisplayName("isCancellation should return false for non-cancellation")
    public void testIsCancellationNonCancellation() {
        when(repository.findByCode("9")).thenReturn(Optional.of(original));
        assertFalse(MessageFunctionCodeAdapter.isCancellation("9"));
    }

    @Test
    @DisplayName("isChange should return true for change")
    public void testIsChange() {
        when(repository.findByCode("4")).thenReturn(Optional.of(change));
        assertTrue(MessageFunctionCodeAdapter.isChange("4"));
    }

    @Test
    @DisplayName("isChange should return false for non-change")
    public void testIsChangeNonChange() {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));
        assertFalse(MessageFunctionCodeAdapter.isChange("1"));
    }

    @Test
    @DisplayName("isReplacement should return true for replacement")
    public void testIsReplacement() {
        when(repository.findByCode("5")).thenReturn(Optional.of(replacement));
        assertTrue(MessageFunctionCodeAdapter.isReplacement("5"));
    }

    @Test
    @DisplayName("isReplacement should return false for non-replacement")
    public void testIsReplacementNonReplacement() {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));
        assertFalse(MessageFunctionCodeAdapter.isReplacement("1"));
    }

    @Test
    @DisplayName("isConfirmation should return false for non-confirmation")
    public void testIsConfirmation() {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));
        assertFalse(MessageFunctionCodeAdapter.isConfirmation("1"));
    }

    @Test
    @DisplayName("isModification should return false for null")
    public void testIsModificationNull() {
        assertFalse(MessageFunctionCodeAdapter.isModification(null));
    }

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("1")).thenReturn(Optional.of(cancellation));

        String marshaled = adapter.marshal(cancellation);
        MessageFunctionCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(cancellation.getCode(), unmarshaled.getCode());
    }
}
