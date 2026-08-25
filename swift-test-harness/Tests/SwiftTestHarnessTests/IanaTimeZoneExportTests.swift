import Testing
import IanaTimeZone

// Smoke test for the Kotlin → Swift Export → SPM → swift test pipeline.
@Suite("IanaTimeZone Swift Export Smoke Tests")
struct IanaTimeZoneExportTests {
    @Test("IanaTimeZone swift module imported cleanly")
    func testSwiftModuleLoads() {
        #expect(Bool(true))
    }
}
