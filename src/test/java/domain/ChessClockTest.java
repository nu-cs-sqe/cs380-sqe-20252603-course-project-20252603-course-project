package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class ChessClockTest {

	@Test
	public void chessClockIsCreatedWithBothPlayersHavingStartingTime() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}

	@Test
	public void nullTimeControlThrows() {
		Clock clockMock = EasyMock.createMock(Clock.class);
		Assertions.assertThrows(
				NullPointerException.class,
				() -> new ChessClock(null, clockMock)
		);
	}

	@Test
	public void nullClockThrows() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Assertions.assertThrows(
				NullPointerException.class,
				() -> new ChessClock(control, null)
		);
	}

	@Test
	public void startWhiteSetsWhiteAsRunning() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"));
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);

		Assertions.assertEquals(Color.WHITE, chessClock.running());
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
		EasyMock.verify(clockMock);
	}

	@Test
	public void startBlackSetsBlackAsRunning() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"));
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.BLACK);

		Assertions.assertEquals(Color.BLACK, chessClock.running());
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
		EasyMock.verify(clockMock);
	}

	@Test
	public void startWithNullActiveThrows() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		Assertions.assertThrows(
				NullPointerException.class,
				() -> chessClock.start(null)
		);
	}

	@Test
	public void tickWhenNotRunningKeepsStartingTime() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.tick();

		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}

	@Test
	public void tickWithZeroElapsedKeepsStartingTime() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"))
		        .times(2);
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);
		chessClock.tick();

		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
		EasyMock.verify(clockMock);
	}

	@Test
	public void tickWithOneSecondElapsedDeductsFromRunning() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"));
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:01Z"));
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);
		chessClock.tick();

		Assertions.assertEquals(
				Duration.ofMinutes(5).minusSeconds(1),
				chessClock.remaining(Color.WHITE)
		);
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
		EasyMock.verify(clockMock);
	}

	@Test
	public void tickWithStartingTimeElapsedReachesZero() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"));
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:05:00Z"));
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);
		chessClock.tick();

		Assertions.assertEquals(Duration.ZERO, chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
		EasyMock.verify(clockMock);
	}

	@Test
	public void tickPastZeroClampsAtZero() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"));
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:06:00Z"));
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);
		chessClock.tick();

		Assertions.assertEquals(Duration.ZERO, chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
		EasyMock.verify(clockMock);
	}

	@Test
	public void completeTurnSwitchesRunningPlayerWithNoIncrement() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"));
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:01Z"));
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);
		chessClock.tick();
		chessClock.completeTurn(Color.WHITE, Color.BLACK);

		Assertions.assertEquals(
				Duration.ofMinutes(5).minusSeconds(1),
				chessClock.remaining(Color.WHITE)
		);
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
		Assertions.assertEquals(Color.BLACK, chessClock.running());
		EasyMock.verify(clockMock);
	}

	@Test
	public void completeTurnAddsIncrementToMovedPlayer() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ofSeconds(3));
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"));
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:01Z"));
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);
		chessClock.tick();
		chessClock.completeTurn(Color.WHITE, Color.BLACK);

		Assertions.assertEquals(
				Duration.ofMinutes(5).minusSeconds(1).plusSeconds(3),
				chessClock.remaining(Color.WHITE)
		);
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
		Assertions.assertEquals(Color.BLACK, chessClock.running());
		EasyMock.verify(clockMock);
	}

	@Test
	public void completeTurnWithSamePlayerThrows() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);

		Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> chessClock.completeTurn(Color.WHITE, Color.WHITE)
		);
	}

	@Test
	public void completeTurnWithNullMovedThrows() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);

		Assertions.assertThrows(
				NullPointerException.class,
				() -> chessClock.completeTurn(null, Color.BLACK)
		);
	}

	@Test
	public void completeTurnWithNullNextThrows() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);

		Assertions.assertThrows(
				NullPointerException.class,
				() -> chessClock.completeTurn(Color.WHITE, null)
		);
	}

	@Test
	public void pauseBeforeStartingDoesNothing() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.pause();

		Assertions.assertNull(chessClock.running());
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}

	@Test
	public void pauseStopsTimeFromGoingDown() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"));
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:01Z"));
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);
		chessClock.tick();
		chessClock.pause();
		chessClock.tick();

		Assertions.assertEquals(
				Duration.ofMinutes(5).minusSeconds(1),
				chessClock.remaining(Color.WHITE)
		);
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
		EasyMock.verify(clockMock);
	}

	@Test
	public void remainingForWhiteIsStartingTime() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
	}

	@Test
	public void remainingForBlackIsStartingTime() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}

	@Test
	public void remainingWithNullColorThrows() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		Assertions.assertThrows(
				NullPointerException.class,
				() -> chessClock.remaining(null)
		);
	}

	@Test
	public void isExpiredReturnsFalseWhenTimeRemains() {
		TimeControl control = new TimeControl(Duration.ofSeconds(1), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		Assertions.assertFalse(chessClock.isExpired(Color.WHITE));
	}

	@Test
	public void isExpiredReturnsTrueWhenTimeIsZero() {
		TimeControl control = new TimeControl(Duration.ofSeconds(1), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:00Z"));
		EasyMock.expect(clockMock.instant())
		        .andReturn(Instant.parse("1970-01-01T00:00:01Z"));
		EasyMock.replay(clockMock);
		ChessClock chessClock = new ChessClock(control, clockMock);

		chessClock.start(Color.WHITE);
		chessClock.tick();

		Assertions.assertTrue(chessClock.isExpired(Color.WHITE));
		EasyMock.verify(clockMock);
	}

	@Test
	public void isExpiredWithNullColorThrows() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clockMock = EasyMock.createMock(Clock.class);
		ChessClock chessClock = new ChessClock(control, clockMock);

		Assertions.assertThrows(
				NullPointerException.class,
				() -> chessClock.isExpired(null)
		);
	}

}
